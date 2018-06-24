package mx.com.wolf.shop.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
data class JwtToken (
    @SerializedName("token")
    @Expose
    var token: String
)