package mx.com.wolf.shop.extensions

import android.support.design.widget.TextInputLayout
import android.widget.TextView

/**
 * Created by Jose Barrera on 26/06/18.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */

fun TextInputLayout.getText() = editText?.text.toString()
fun TextInputLayout.clean() {editText?.setText("", TextView.BufferType.EDITABLE)}