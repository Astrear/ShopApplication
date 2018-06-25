package mx.com.wolf.shop.di

import android.arch.lifecycle.ViewModelProvider
import dagger.Component
import mx.com.wolf.shop.ShopApplication
import mx.com.wolf.shop.data.source.ItemRepository
import mx.com.wolf.shop.data.source.LoginApi
import mx.com.wolf.shop.di.module.ApplicationModule
import mx.com.wolf.shop.di.module.ItemRepositoryModule
import mx.com.wolf.shop.di.module.NetModule
import mx.com.wolf.shop.di.module.ViewModelModule
import javax.inject.Singleton

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
@Singleton
@Component(modules = [
    ApplicationModule::class,
    ViewModelModule::class,
    NetModule::class,
    ItemRepositoryModule::class
])
interface ApplicationComponent {
    fun inject(shopApplication: ShopApplication)

    fun getItemRepository(): ItemRepository
    fun getLoginApi(): LoginApi
    fun getViewModelFactory(): ViewModelProvider.Factory
}