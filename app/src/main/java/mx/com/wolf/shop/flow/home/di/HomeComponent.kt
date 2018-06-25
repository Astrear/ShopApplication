package mx.com.wolf.shop.flow.home.di

import dagger.Component
import mx.com.wolf.shop.di.ApplicationComponent
import mx.com.wolf.shop.flow.home.HomeActivity
import mx.com.wolf.shop.mvp.di.ActivityScope

/**
 * Created by Jose Barrera on 24/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [HomeModule::class])
interface HomeComponent {
    fun inject(homeActivity: HomeActivity)
}