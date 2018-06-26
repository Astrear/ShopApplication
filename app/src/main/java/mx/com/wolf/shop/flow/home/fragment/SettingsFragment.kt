package mx.com.wolf.shop.flow.home.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dd.processbutton.iml.ActionProcessButton
import mx.com.wolf.shop.R
import mx.com.wolf.shop.extensions.setSessionToken
import mx.com.wolf.shop.extensions.start
import mx.com.wolf.shop.flow.home.HomeActivity

/**
 * Created by Jose Barrera on 25/06/18.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
class SettingsFragment: Fragment() {

    lateinit var logoutButton: ActionProcessButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_settings, container, false)

        logoutButton = view.findViewById<ActionProcessButton>(R.id.button_logout).apply {
            setMode(ActionProcessButton.Mode.ENDLESS)
            setOnClickListener(logoutButtonListener)
        }

        return view
    }

    private val logoutButtonListener = View.OnClickListener {
        (it as ActionProcessButton).start()
        activity.setSessionToken("")
        (activity as HomeActivity).showLogin()
    }
}