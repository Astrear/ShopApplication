package mx.com.wolf.shop.data.source.local

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

    override fun listItems(callback: ItemDataSource.ListItemsCallback) {
        Runnable {
            val items = itemDAO.getAll()
            executors.mainThread.execute {
                if(items.isEmpty()) callback.onDataUnavailable()
                else callback.onSuccess(items)
            }
        }.let { executors.diskIO.execute(it) }
    }

    override fun getItem(itemId: Int, callback: ItemDataSource.GetItemCallback) {
        Runnable {
            val item = itemDAO.get(itemId)
            executors.mainThread.execute {
                if(item == null) callback.onDataUnavailable()
                else callback.onSuccess(item)
            }
        }.let { executors.diskIO.execute(it) }
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

}