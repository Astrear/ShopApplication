package mx.com.wolf.shop.data.source.local

import android.arch.lifecycle.LiveData
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import mx.com.wolf.shop.data.Item
import mx.com.wolf.shop.data.source.ItemDataSource
import mx.com.wolf.shop.util.ApplicationExecutors
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
@Singleton
class ItemLocalDataSource
@Inject constructor(
        var executors: ApplicationExecutors,
        var itemDAO: ItemDAO
): ItemDataSource {


    fun getItems(): Single<LiveData<List<Item>>> {
        return Single.fromCallable({ itemDAO.getItems() })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getItem(itemId: Int, callback: ItemDataSource.GetItemCallback) {
        Runnable {
            val item = itemDAO.get(itemId)
            executors.mainThread.execute {
                if(item == null) callback.onError()
                else callback.onSuccess(item)
            }
        }.let { executors.diskIO.execute(it) }
    }

    override fun addItems(items: List<Item>) {
        Runnable {
            itemDAO.insertItems(*items.toTypedArray())
        }.let {executors.diskIO.execute(it)}
    }

    override fun addItem(item: Item) {
        Runnable {
            itemDAO.insert(item)
        }.let {executors.diskIO.execute(it)}
    }

    override fun deleteItem(itemId: Int) {
        Runnable {
            executors.mainThread.execute {
                itemDAO.delete(itemId)
            }
        }.let { executors.diskIO.execute(it) }
    }

    interface LoadItemsCallback {
        fun onSuccess(items: LiveData<List<Item>>)
        fun onError(message: String = "", data: Boolean = false)
    }
}