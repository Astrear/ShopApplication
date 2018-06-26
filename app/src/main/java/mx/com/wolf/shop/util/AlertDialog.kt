package mx.com.wolf.shop.util

import android.content.Context
import android.content.DialogInterface
import android.os.CountDownTimer
import android.support.v7.app.AlertDialog
import mx.com.wolf.shop.R

/**
 * Created by Jose Barrera on 25/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
class AlertDialog(private val context: Context) {

    private var defaultPositiveText: String = context.getString(R.string.dialog_okbutton)
    private var defaultNegativeText: String = context.getString(R.string.dialog_cancelbutton)
    private val builder: AlertDialog.Builder = AlertDialog.Builder(context)
    private var dialog: AlertDialog? = null

    private lateinit var countDownTimer: CountDownTimer

    fun setupDialog(title: String, message: String) {
        dialog = builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .create()
    }

    fun addButton(button: DialogButton) {
        val type = when(button.type) {
            DialogButton.POSITIVE -> DialogInterface.BUTTON_POSITIVE
            DialogButton.NEGATIVE -> DialogInterface.BUTTON_NEGATIVE
            else -> DialogInterface.BUTTON_NEUTRAL
        }
        setDefaultText(button.type, button.text)
        dialog?.setButton(type, button.text, { dialog, _ ->
            button.callback()
            dialog.dismiss()
        })
    }

    fun show() = dialog?.show()

    fun getContext() = context

    fun getDefaultText(type: String): String =
            if(type == DialogButton.POSITIVE) defaultPositiveText else defaultNegativeText

    private fun getButton(type: String) = when(type) {
        DialogButton.POSITIVE -> dialog?.getButton(DialogInterface.BUTTON_POSITIVE)
        DialogButton.NEGATIVE -> dialog?.getButton(DialogInterface.BUTTON_NEGATIVE)
        else -> dialog?.getButton(AlertDialog.BUTTON_NEUTRAL)
    }

    private fun setDefaultText(type: String, text: String) =
            if(type == DialogButton.POSITIVE) defaultPositiveText = text else defaultNegativeText = text
}