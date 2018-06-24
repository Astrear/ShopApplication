package mx.com.wolf.shop.di

import dagger.Component
import mx.com.wolf.shop.ShopApplication
import mx.com.wolf.shop.data.source.ItemRepository
import mx.com.wolf.shop.di.module.ApplicationModule
import mx.com.wolf.shop.di.module.ItemRepositoryModule
import mx.com.wolf.shop.di.module.NetModule
import javax.inject.Singleton

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
@Singleton
@Component(modules = [
    ApplicationModule::class,
    ItemRepositoryModule::class,
    NetModule::class
])
interface ApplicationComponent {
    fun inject(shopApplication: ShopApplication)

    fun getItemRepository(): ItemRepository
}