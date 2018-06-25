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

    @GET("/items/")
    fun getItems(): Maybe<List<Item>>

    @GET("/items/{itemId}/")
    fun getItem(@Path("itemId") itemId: Int): Maybe<Item>

    @Headers("Content-Type: application/json")
    @POST("/items/")
    fun addItem(@Body itemRequest: ItemRequest): Maybe<Item>

    @DELETE("/items/{itemId}/")
    fun deleteItem(@Path("itemId") itemId: Int): Completable

}