package mx.com.wolf.shop.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo



/**
 * Created by Jose Barrera on 24/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */

private const val SESSION = "session"

fun Context.setSessionToken(jwtToken: String) =
    getSharedPreferences(SESSION, Context.MODE_PRIVATE).edit().putString("token", jwtToken).apply()


fun Context.getSessionToken(): String
        = getSharedPreferences(SESSION, Context.MODE_PRIVATE).getString("token", "")

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}