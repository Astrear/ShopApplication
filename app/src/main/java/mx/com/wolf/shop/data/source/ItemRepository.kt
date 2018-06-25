package mx.com.wolf.shop.data.source

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.util.Log
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
            getItemsFromRemoteSOurce()
        } else {
            localDataSource.getItems()
                    .subscribe { items ->
                        if(items.value != null && items.value!!.isEmpty())
                            this.items!!.value = items.value
                        else getItemsFromRemoteSOurce()
                    }
        }
        return items!!
    }

    private fun getItemsFromRemoteSOurce() {
        remoteDataSource.getItems()
                .first(mutableListOf())
                .subscribe { items ->
                    for(item in items)
                        localDataSource.addItem(item)
                    this.items!!.value = items
                }
    }

    fun getItem(itemId: Int, callback: ItemDataSource.GetItemCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun addItem(item: Item) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun deleteItem(itemId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}