package mx.com.wolf.shop.extensions

import com.dd.processbutton.iml.ActionProcessButton

/**
 * Created by Jose Barrera on 26/06/18.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */


fun ActionProcessButton.start() = apply {progress = 1}.apply {isEnabled = false}
fun ActionProcessButton.error() = apply {progress = -1}.apply {isEnabled = true}
fun ActionProcessButton.success() = apply {progress = 100}.apply {isEnabled = true}
fun ActionProcessButton.reset() = apply {progress = 0}.apply {isEnabled = true}