package mx.com.wolf.shop.flow.login

import mx.com.wolf.shop.mvp.BasePresenter
import mx.com.wolf.shop.mvp.BaseView

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
object LoginContract {
    interface View: BaseView {
        fun showError(message: String)
    }

    abstract class Presenter: BasePresenter<View>() {
        abstract fun login()
        abstract fun login(username: String, password: String)
    }
}