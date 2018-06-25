package mx.com.wolf.shop.flow.home

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import mx.com.wolf.shop.data.source.ItemRepository
import mx.com.wolf.shop.data.source.local.ItemViewModel
import javax.inject.Inject

/**
 * Created by Jose Barrera on 24/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
class HomePresenter
@Inject constructor(
        var itemRepository: ItemRepository
): HomeContract.Presenter()  {

    override fun getItemList() {
        val model = ViewModelProviders.of(getView() as HomeActivity).get(ItemViewModel::class.java)
    }

    override fun addItem(name: String, image: String, desc: String) {

    }

    override fun deleteItem(itemId: String) {

    }
}