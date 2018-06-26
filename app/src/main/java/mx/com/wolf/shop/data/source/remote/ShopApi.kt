package mx.com.wolf.shop.data.source.remote

import io.reactivex.Completable
import io.reactivex.Maybe
import mx.com.wolf.shop.data.Item
import mx.com.wolf.shop.data.ItemRequest
import mx.com.wolf.shop.data.JwtToken
import mx.com.wolf.shop.data.Login
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
interface ShopApi {

    @Headers("Content-Type: application/json")
    @POST("/api-token-auth/")
    fun getJwtToken(@Body login: Login): Maybe<Response<JwtToken>>

    @Headers("Content-Type: application/json")
    @POST("/api-token-refresh/")
    fun refreshJwtToken(@Body token: JwtToken): Maybe<Response<JwtToken>>

    @Headers("Content-Type: application/json")
    @POST("/api-token-verify/")
    fun verifyJwtToken(@Body token: JwtToken): Maybe<Response<JwtToken>>

    @GET("/items/")
    fun getItems(@Header("Authorization") token: String): Maybe<List<Item>>

    @GET("/items/{id}/")
    fun getItem(
            @Header("Authorization") token: String,
            @Path("id") itemId: Int
    ): Maybe<Item>

    @Headers("Content-Type: application/json")
    @POST("/items/")
    fun addItem(
            @Header("Authorization") token: String,
            @Body itemRequest: ItemRequest
    ): Maybe<Item>

    @DELETE("/items/{id}/")
    fun deleteItem(
            @Header("Authorization") token: String,
            @Path("id") itemId: Int
    ): Completable

}