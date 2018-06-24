package mx.com.wolf.shop.data.source

import mx.com.wolf.shop.data.Item
import mx.com.wolf.shop.data.JwtToken

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
interface ItemDataSource {

    interface ListItemsCallback {
        fun onSuccess(items: List<Item>)
        fun onDataUnavailable()
    }

    interface GetItemCallback {
        fun onSuccess(item: Item)
        fun onDataUnavailable()
    }

    fun listItems(callback: ListItemsCallback)
    fun getItem(itemId: Int, callback: GetItemCallback)
    fun addItem(item: Item)
    fun deleteItem(itemId: Int)
}