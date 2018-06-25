package mx.com.wolf.shop.flow.login

import android.util.Log
import mx.com.wolf.shop.data.JwtToken
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

    override fun login(username: String, password: String) {
        loginApi.getJwtToken(username, password, object: LoginApi.GetJwtTokenCallback {

            override fun onSuccess(jwtToken: JwtToken) {
                Log.i(LoginActivity.TAG, "Success getting session token: ${jwtToken.token}")

                with(getView() as LoginActivity) {
                    loginButton.progress = 100
                    if(getSessionToken() != jwtToken.token) {
                        setSessionToken(jwtToken.token)
                        showHome()
                    }
                    else showHome()
                }
            }

            override fun onError(error: String) {
                with(getView() as LoginActivity) {
                    loginButton.progress = -1
                    loginButton.isEnabled = true
                    showError(error)
                }
            }

        })
    }
}