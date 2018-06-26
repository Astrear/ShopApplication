package mx.com.wolf.shop.flow.login

import android.util.Log
import io.reactivex.rxkotlin.subscribeBy
import mx.com.wolf.shop.data.source.LoginApi
import mx.com.wolf.shop.extensions.*
import javax.inject.Inject

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
class LoginPresenter
@Inject constructor(
        var loginApi: LoginApi
): LoginContract.Presenter()  {

    override fun login() {
        with(getView() as LoginActivity) {
            if (isNetworkAvailable()) {
                val sessionToken = getSessionToken()
                if (sessionToken.isNotBlank())
                    loginApi.verifyJwtToken(sessionToken)
                            .subscribe {
                                if (it.isSuccessful) {
                                    Log.i(LoginActivity.TAG, "Current token is valid: ${it.body()!!.token}")
                                    showHome()
                                } else {
                                    Log.i(LoginActivity.TAG, "Error: ${it.errorBody()}")
                                }
                            }
                else Log.i(LoginActivity.TAG, "Missing or expired token")
            } else showError("Network connection needed to verify session token")
        }
    }

    override fun login(username: String, password: String) {
        with(getView() as LoginActivity){
            if(isNetworkAvailable())
                loginApi.getJwtToken(username, password).subscribeBy(
                        onSuccess = {response ->
                            if (response.isSuccessful) {
                                Log.i(LoginActivity.TAG, "New session token: ${response.body()!!.token}")
                                loginButton.success()
                                setSessionToken(response.body()!!.token)
                                showHome()
                            } else {
                                loginButton.error()
                                when (response.code()) {
                                    400 -> showError("Incorrect Username/Password")
                                    else -> showError("Server error code: ${response.code()}")
                                }
                            }
                        },
                        onError = {
                            loginButton.error()
                            showError(it.message!!)
                        }
                )
            else {
                loginButton.error()
                showError("Cannot connect to server")
            }
        }
    }
}