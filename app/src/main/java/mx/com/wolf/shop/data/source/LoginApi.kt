package mx.com.wolf.shop.data.source

import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import mx.com.wolf.shop.data.JwtToken
import mx.com.wolf.shop.data.Login
import mx.com.wolf.shop.data.source.remote.ShopApi
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Jose Barrera on 24/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
@Singleton
class LoginApi @Inject constructor(var api: ShopApi) {

    fun getJwtToken(username: String, password: String): Maybe<Response<JwtToken>> =
        api.getJwtToken(Login(username, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    fun verifyJwtToken(token: String): Maybe<Response<JwtToken>> =
        api.verifyJwtToken(JwtToken(token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
}