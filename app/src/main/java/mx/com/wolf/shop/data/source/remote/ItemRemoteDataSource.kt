package mx.com.wolf.shop.data.source.remote

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import mx.com.wolf.shop.data.Item
import mx.com.wolf.shop.data.ItemRequest
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
@Singleton
class ItemRemoteDataSource
@Inject constructor(var shopApi: ShopApi) {

    fun getItems(token: String): Flowable<List<Item>> {
        return shopApi.getItems(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toFlowable()
    }

    fun getItem(token: String, itemId: Int) =
            shopApi.getItem(token, itemId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())!!

    fun addItem(token: String, item: Item): Flowable<Item>  =
            shopApi.addItem(token, ItemRequest(item.name, item.image, item.description))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .toFlowable()

    fun deleteItem(token: String, itemId: Int): Completable =
            shopApi.deleteItem(token, itemId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
}