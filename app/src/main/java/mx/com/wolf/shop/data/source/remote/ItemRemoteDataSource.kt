package mx.com.wolf.shop.data.source.remote

import android.util.Log
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import mx.com.wolf.shop.data.Item
import mx.com.wolf.shop.data.Login
import mx.com.wolf.shop.data.source.ItemDataSource
import mx.com.wolf.shop.data.source.JwtDataSource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
@Singleton
class ItemRemoteDataSource
@Inject constructor(
        var shopApi: ShopApi
): JwtDataSource {

    override fun getJwtToken(
            username: String,
            password: String,
            callback: JwtDataSource.GetJwtTokenCallback
    ) {
        shopApi.getJwtToken(Login(username, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {callback.onSuccess(it)},
                        onError = {callback.onError(it.message!!)}
                )
    }

    fun listItems(callback: ItemDataSource.ListItemsCallback): Disposable {
        return shopApi.listItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {callback.onSuccess(it)},
                        onError =  {callback.onDataUnavailable()}
                )
    }

    fun getItem(itemId: Int, callback: ItemDataSource.GetItemCallback): Disposable {
        return shopApi.getItem(itemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {callback.onSuccess(it)},
                        onError = {callback.onDataUnavailable()}
                )
    }

    fun addItem(item: Item)  {
        Completable.fromAction {
            shopApi.addItem(item)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onComplete = {Log.i("test", "complete")},
                        onError = {Log.e("test",it.message)}
                )
    }

    fun deleteItem(itemId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}