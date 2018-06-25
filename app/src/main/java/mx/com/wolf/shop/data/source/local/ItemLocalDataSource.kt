package mx.com.wolf.shop.data.source.local

import android.arch.lifecycle.LiveData
import android.util.Log
import io.reactivex.Completable
import io.reactivex.Flowable
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
) {


    fun getItems(): Single<LiveData<List<Item>>> {
        return Single.fromCallable({ itemDAO.getItems() })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getItem(itemId: Int): Flowable<Item> =
            itemDAO.get(itemId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .toFlowable()

    fun addItem(item: Item): Flowable<Long> =
            Completable.fromAction {
                itemDAO.insert(item)
            }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .toFlowable()

    fun deleteItem(itemId: Int) {
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