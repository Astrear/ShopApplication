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

    companion object {
        val TAG = ItemRepository::class.simpleName
    }

    private var items: MutableLiveData<List<Item>>? = null
    private var cacheIsDirty: Boolean = false

    fun getItems(token: String): LiveData<List<Item>> {
        if(items != null && !cacheIsDirty)
            return items!!
            items = MutableLiveData()


        if(cacheIsDirty) {
            getItemsFromRemoteSource(token)
        } else {
            Log.i(TAG, "Getting items from local source")
            localDataSource.getItems()
                    .firstElement()
                    .doOnError { Log.i(TAG, it.message) }
                    .subscribe { items ->
                        Log.i(TAG, "in subscribe")
                        if(items.value != null && items.value!!.isNotEmpty()) {
                            this.items!!.value = items.value
                        } else getItemsFromRemoteSource(token)

                    }
        }
        return items!!
    }

    private fun getItemsFromRemoteSource(token: String) {
        Log.i(TAG, "Getting items from remote source")
        remoteDataSource.getItems(token)
                .first(mutableListOf())
                .onErrorReturn { mutableListOf() }
                .subscribe { items ->
                    for(item in items)
                        localDataSource.addItem(item)
                    this.items!!.value = items
                    cacheIsDirty = false
                }
    }

    fun getItem(token: String, itemId: Int, callback: UpdateCallback) {
        remoteDataSource.getItem(token, itemId).subscribeBy(
            onSuccess = {callback.onSuccess(it)},
            onError = {callback.onError(it.message!!)}
        )
    }

    fun addItem(token: String, item: Item, callback: UpdateCallback) {
        remoteDataSource.addItem(token, item)
                .onErrorReturn { null }
                .doOnError { callback.onError(it.message!!) }
                .firstElement()
                .subscribe {
                    cacheIsDirty = true
                    callback.onSuccess(it)
                }
    }

    fun deleteItem(token: String, itemId: Int, callback: DeleteCallback) {
        remoteDataSource.deleteItem(token, itemId)
                .doOnError { Log.i(TAG, it.message) }
                .subscribe {
                    cacheIsDirty = true
                    refreshCachedItems(token)
                    callback.onSuccess()
                }
    }

    private fun refreshCachedItems(token: String) {
        this.items!!.value = getItems(token).value
    }

    interface UpdateCallback {
        fun onSuccess(item: Item)
        fun onError(message: String)
    }

    interface DeleteCallback {
        fun onSuccess()
    }

}