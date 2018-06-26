package mx.com.wolf.shop.flow.home

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.util.Log
import io.reactivex.rxkotlin.subscribeBy
import mx.com.wolf.shop.data.Item
import mx.com.wolf.shop.data.source.ItemRepository
import mx.com.wolf.shop.data.source.local.ItemViewModel
import mx.com.wolf.shop.extensions.getSessionToken
import mx.com.wolf.shop.flow.home.fragment.AddFragment
import javax.inject.Inject

/**
 * Created by Jose Barrera on 24/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
class HomePresenter : HomeContract.Presenter()