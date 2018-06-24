package mx.com.wolf.shop.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
@Module
class ApplicationModule(private val context: Application) {

    @Provides
    @Singleton
    internal fun provideApplicationContext(): Context = context
}