package mx.com.wolf.shop.data.source.remote

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import mx.com.wolf.shop.data.Item
import mx.com.wolf.shop.data.ItemRequest
import mx.com.wolf.shop.data.source.ItemDataSource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
@Singleton
class ItemRemoteDataSource
@Inject constructor(var shopApi: ShopApi): ItemDataSource {


    fun getItems(): Flowable<List<Item>> {
        return shopApi.getItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toFlowable()
    }

    override fun getItem(itemId: Int, callback: ItemDataSource.GetItemCallback) {
        shopApi.getItem(itemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {callback.onSuccess(it)}
                )
    }

    override fun addItems(items: List<Item>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addItem(item: Item)  {
        Completable.fromAction {
            shopApi.addItem(ItemRequest(item.name, item.image, item.description))
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onComplete = {Log.i("test", "complete")},
                        onError = {Log.e("test",it.message)}
                )
    }

    override fun deleteItem(itemId: Int) {
        Completable.fromAction {
            shopApi.deleteItem(itemId)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onComplete = {Log.i("test", "complete")},
                        onError = {Log.e("test",it.message)}
                )
    }

    interface LoadItemsCallback {
        fun onSuccess(items:List<Item>)
    }
}