package mx.com.wolf.shop.data.source

import mx.com.wolf.shop.data.Item

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
interface ItemDataSource {

    interface LoadItemsCallback {
        fun onSuccess(items: List<Item>)
        fun onError()
    }

    interface GetItemCallback {
        fun onSuccess(item: Item)
        fun onError()
    }

    fun getItem(itemId: Int, callback: GetItemCallback)
    fun addItems(items: List<Item>)
    //fun addItem(item: Item)
    fun deleteItem(itemId: Int)
}