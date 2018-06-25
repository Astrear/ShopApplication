package mx.com.wolf.shop.data.source.local

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.AsyncTask
import mx.com.wolf.shop.data.Item
import javax.inject.Inject

/**
 * Created by Jose Barrera on 24/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
class ItemViewModel
@Inject constructor(private val itemDAO: ItemDAO): ViewModel() {

    private var items: MutableLiveData<List<Item>>? = null

    fun getItemsList(): LiveData<Item>? {
        if(items == null) {
            items = MutableLiveData()
            loadItems()
        }
        return null
    }

    fun addItem(item: Item) {
        DoAsync {
            itemDAO.insert(item)
        }.execute()
    }

    fun deleteItem(itemId: Int) {
        DoAsync {itemDAO.delete(itemId)}.execute()
    }

    fun loadItems() {
        DoAsync {

        }.execute()
    }

    class DoAsync(private val command: () -> Unit): AsyncTask<Item, Any, Any>() {
        override fun doInBackground(vararg params: Item?) {command}
    }
}