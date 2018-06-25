package mx.com.wolf.shop.flow.login.di

import dagger.Module
import dagger.Provides
import mx.com.wolf.shop.data.source.LoginApi
import mx.com.wolf.shop.flow.login.LoginPresenter
import mx.com.wolf.shop.mvp.di.ActivityScope

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
@Module
class LoginModule {
    @Provides
    @ActivityScope
    internal fun provideLoginPresenter(api: LoginApi) = LoginPresenter(api)
}