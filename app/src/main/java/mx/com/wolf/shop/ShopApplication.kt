package mx.com.wolf.shop

import android.app.Application
import mx.com.wolf.shop.di.ApplicationComponent
import mx.com.wolf.shop.di.DaggerApplicationComponent
import mx.com.wolf.shop.di.module.ApplicationModule
import mx.com.wolf.shop.di.module.ItemRepositoryModule
import mx.com.wolf.shop.di.module.NetModule

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
class ShopApplication: Application() {

    companion object {
        const val BASE_URL = "http://159.89.114.121:8000"
    }

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .netModule(NetModule(BASE_URL))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        inject()
    }

    fun inject() = applicationComponent.inject(this)
}