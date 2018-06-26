package mx.com.wolf.shop.extensions

import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity

/**
 * Created by Jose Barrera on 26/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
fun AppCompatActivity.isPermissionGranted(permission: String) =
        ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun AppCompatActivity.shouldShowPermissionRationale(permission: String) =
        ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

fun AppCompatActivity.requestPermission(permission: String, requestId: Int) =
        ActivityCompat.requestPermissions(this, arrayOf(permission), requestId)

fun AppCompatActivity.batchRequestPermissions(permissions: Array<String>, requestId: Int) =
        ActivityCompat.requestPermissions(this, permissions, requestId)

fun AppCompatActivity.hasPermissions(permissions: Array<String>): Boolean {
    for(permission in permissions)
        if(!isPermissionGranted(permission))
            return false
    return true
}