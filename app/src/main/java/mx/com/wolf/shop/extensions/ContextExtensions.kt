package mx.com.wolf.shop.extensions

import android.content.Context

/**
 * Created by Jose Barrera on 24/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */

private const val SESSION = "session"

fun Context.setSessionToken(jwtToken: String) =
    getSharedPreferences(SESSION, Context.MODE_PRIVATE).edit().putString("token", jwtToken).apply()


fun Context.getSessionToken(): String
        = getSharedPreferences(SESSION, Context.MODE_PRIVATE).getString("token", "")

