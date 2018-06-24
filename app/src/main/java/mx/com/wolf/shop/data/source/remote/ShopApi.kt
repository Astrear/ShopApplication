package mx.com.wolf.shop.data.source.remote

import io.reactivex.Maybe
import mx.com.wolf.shop.data.Item
import mx.com.wolf.shop.data.JwtToken
import mx.com.wolf.shop.data.Login
import retrofit2.http.*

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
interface ShopApi {

    @Headers("Content-Type: application/json")
    @POST("/api-token-auth/")
    fun getJwtToken(@Body login: Login): Maybe<JwtToken>

    @GET("/items")
    fun listItems(): Maybe<List<Item>>

    @GET("/items/{itemId}")
    fun getItem(@Path("itemId") itemId: Int): Maybe<Item>

    @POST("/items")
    fun addItem(item: Item)

    @DELETE("/items/{itemId}")
    fun deleteItem(@Path("itemId") itemId: Int)

}