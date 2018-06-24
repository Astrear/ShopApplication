package mx.com.wolf.shop.mvp

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
abstract class BasePresenter<out V> {

    private var view: V? = null

    fun getView(): V? = this.view

    @Suppress("UNCHECKED_CAST")
    fun attachView(view: Any) {
        this.view = view as V
    }

    fun detachView() {
        this.view = null
    }
}