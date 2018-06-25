package mx.com.wolf.shop.data.source

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import io.reactivex.rxkotlin.subscribeBy
import mx.com.wolf.shop.data.Item
import mx.com.wolf.shop.data.di.Local
import mx.com.wolf.shop.data.di.Remote
import mx.com.wolf.shop.data.source.local.ItemLocalDataSource
import mx.com.wolf.shop.data.source.remote.ItemRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
@Singleton
class ItemRepository
@Inject constructor(
        @Local var localDataSource: ItemLocalDataSource,
        @Remote var remoteDataSource: ItemRemoteDataSource
): ViewModel() {

    var items: MutableLiveData<List<Item>>? = null
    var cacheIsDirty: Boolean = true

    fun getItems(): LiveData<List<Item>> {
        Log.i("Repository", "on getfunction")
        if(items != null && !cacheIsDirty)
            return items!!

        if(items == null)
            items = MutableLiveData()

        if(cacheIsDirty) {
            getItemsFromRemoteSource()
        } else {
            localDataSource.getItems()
                    .subscribe { items ->
                        if(items.value != null && items.value!!.isEmpty())
                            this.items!!.value = items.value
                        else getItemsFromRemoteSource()
                    }
        }
        return items!!
    }

    private fun getItemsFromRemoteSource() {
        remoteDataSource.getItems()
                .first(mutableListOf())
                .onErrorReturn { mutableListOf() }
                .subscribe { items ->
                    for(item in items)
                        localDataSource.addItem(item)
                    this.items!!.value = items
                }
    }

    fun getItem(itemId: Int, callback: UpdateCallback) {
        remoteDataSource.getItem(itemId).subscribeBy(
            onSuccess = {callback.onSuccess(it)},
            onError = {callback.onError(it.message!!)}
        )
    }

    fun addItem(item: Item, callback: UpdateCallback) {
        remoteDataSource.addItem(item)
                .onErrorReturn { null }
                .doOnError { callback.onError(it.message!!) }
                .firstElement()
                .subscribe {
                    callback.onSuccess(it)
                }
    }

    fun deleteItem(itemId: Int, callback: DeleteCallback) {
        remoteDataSource.deleteItem(itemId)
                .subscribe { callback.onSuccess() }
    }

    interface UpdateCallback {
        fun onSuccess(item: Item)
        fun onError(message: String)
    }

    interface DeleteCallback {
        fun onSuccess()
    }

}