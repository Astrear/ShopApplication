package mx.com.wolf.shop.data.source

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
): ItemDataSource, JwtDataSource {

    var cachedElements: MutableMap<Int, Item>? = null
    var cacheIsDirty: Boolean = false


    override fun getJwtToken(
            username: String,
            password: String,
            callback: JwtDataSource.GetJwtTokenCallback
    ) {
        remoteDataSource.getJwtToken(username, password, callback)
    }

    override fun listItems(callback: ItemDataSource.ListItemsCallback) {
        // TODO
    }

    override fun getItem(itemId: Int, callback: ItemDataSource.GetItemCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addItem(item: Item) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteItem(itemId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}