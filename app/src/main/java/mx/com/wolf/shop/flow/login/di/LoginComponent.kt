package mx.com.wolf.shop.flow.login.di

import dagger.Component
import mx.com.wolf.shop.di.ApplicationComponent
import mx.com.wolf.shop.flow.login.LoginActivity
import mx.com.wolf.shop.mvp.di.ActivityScope

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [LoginModule::class])
interface LoginComponent {
    fun inject(loginActivity: LoginActivity)
}