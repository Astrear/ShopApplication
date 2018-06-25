package mx.com.wolf.shop.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Jose Barrera on 24/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
data class Login(
        @SerializedName("username")
        var username: String,

        @SerializedName("password")
        var password: String
)