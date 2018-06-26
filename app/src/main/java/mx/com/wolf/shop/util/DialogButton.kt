package mx.com.wolf.shop.util

/**
 * Created by Jose Barrera on 25/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
data class DialogButton(
        val type: String,
        val text: String,
        val callback: () -> Unit
) {
    companion object {
        const val POSITIVE = "POSITIVE"
        const val NEGATIVE = "NEGATIVE"
    }
}