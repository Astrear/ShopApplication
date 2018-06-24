package mx.com.wolf.shop.flow.login

import android.util.Log
import mx.com.wolf.shop.data.JwtToken
import mx.com.wolf.shop.data.source.ItemRepository
import mx.com.wolf.shop.data.source.JwtDataSource
import javax.inject.Inject

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
class LoginPresenter @Inject constructor(
        var itemRepository: ItemRepository
): LoginContract.Presenter()  {

    override fun login(username: String, password: String) {
        itemRepository.getJwtToken(username, password, object: JwtDataSource.GetJwtTokenCallback {

            override fun onSuccess(jwtToken: JwtToken) {
                Log.i("token", jwtToken.token)
            }

            override fun onError(error: String) {
                (getView() as LoginActivity).showError(error)
            }

        })
    }
}