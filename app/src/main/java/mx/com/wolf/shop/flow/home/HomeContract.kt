package mx.com.wolf.shop.flow.home

import mx.com.wolf.shop.mvp.BasePresenter
import mx.com.wolf.shop.mvp.BaseView

/**
 * Created by Jose Barrera on 24/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
object HomeContract {
    interface View: BaseView {
        fun showItemList()
        fun showNewItem(name: String, image: String, desc: String)
        fun hideItem(itemId: String)
    }

    abstract class Presenter: BasePresenter<View>() {
        abstract fun getItemList()
        abstract fun addItem(name: String, image: String, desc: String)
        abstract fun deleteItem(itemId: String)
    }
}