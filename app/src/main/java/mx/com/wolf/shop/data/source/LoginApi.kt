package mx.com.wolf.shop.data.source

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import mx.com.wolf.shop.data.JwtToken
import mx.com.wolf.shop.data.Login
import mx.com.wolf.shop.data.source.remote.ShopApi
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Jose Barrera on 24/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
@Singleton
class LoginApi @Inject constructor(var api: ShopApi) {

    fun getJwtToken(
            username: String,
            password: String,
            callback: GetJwtTokenCallback
    ) {
        api.getJwtToken(Login(username, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {response ->
                            if(response.isSuccessful)
                                callback.onSuccess(response.body()!!)
                            else when(response.code()) {
                                400 -> callback.onError("Incorrect Username/Password")
                                else -> callback.onError("server Error")
                            }
                        },
                        onError = {callback.onError(it.message!!)}
                )
    }

    interface GetJwtTokenCallback {
        fun onSuccess(jwtToken: JwtToken)
        fun onError(error: String)
    }
}