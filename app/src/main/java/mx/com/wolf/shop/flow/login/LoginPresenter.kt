package mx.com.wolf.shop.flow.login

import android.util.Log
import io.reactivex.rxkotlin.subscribeBy
import mx.com.wolf.shop.data.source.LoginApi
import mx.com.wolf.shop.extensions.getSessionToken
import mx.com.wolf.shop.extensions.setSessionToken
import javax.inject.Inject

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
class LoginPresenter @Inject constructor(
        var loginApi: LoginApi
): LoginContract.Presenter()  {

    override fun login() {
        val sessionToken = (getView() as LoginActivity).getSessionToken()
        if(sessionToken.isNotBlank())
            loginApi.verifyJwtToken(sessionToken)
                .subscribe {
                    if (it.isSuccessful) {
                        Log.i(LoginActivity.TAG, "Current token is valid: ${it.body()!!.token}")
                        (getView() as LoginActivity).showHome()
                    }
                }
        else Log.i(LoginActivity.TAG, "Missing or expired token")
    }

    override fun login(username: String, password: String) {
        loginApi.getJwtToken(username, password)
                .subscribeBy(
                        onSuccess = {response ->
                            with(getView() as LoginActivity) {
                                if (response.isSuccessful) {
                                    Log.i(LoginActivity.TAG, "New session token: ${response.body()!!.token}")
                                    setSessionToken(response.body()!!.token)
                                    showHome()
                                } else when (response.code()) {
                                    400 -> {
                                        loginButton.progress = -1
                                        loginButton.isEnabled = true
                                        showError("Incorrect Username/Password")
                                    }
                                    else -> {
                                        loginButton.progress = -1
                                        loginButton.isEnabled = true
                                        showError("Server error code: ${response.code()}")
                                    }
                                }
                            }
                        },
                        onError = {
                            with(getView() as LoginActivity) {
                                loginButton.progress = -1
                                loginButton.isEnabled = true
                                showError(it.message!!)
                            }
                        }
                )
    }
}