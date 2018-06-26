package mx.com.wolf.shop.flow.home

import mx.com.wolf.shop.mvp.BasePresenter
import mx.com.wolf.shop.mvp.BaseView

/**
 * Created by Jose Barrera on 24/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
object HomeContract {
    interface View: BaseView

    abstract class Presenter: BasePresenter<View>()
}