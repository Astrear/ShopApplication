package mx.com.wolf.shop.data.source

import mx.com.wolf.shop.data.JwtToken

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
interface JwtDataSource {
    interface GetJwtTokenCallback {
        fun onSuccess(jwtToken: JwtToken)
        fun onError(error: String)
    }

    fun getJwtToken(
            username: String,
            password: String,
            callback: GetJwtTokenCallback
    )
}