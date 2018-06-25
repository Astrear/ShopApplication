package mx.com.wolf.shop.flow.home.di

import dagger.Module
import dagger.Provides
import mx.com.wolf.shop.data.source.ItemRepository
import mx.com.wolf.shop.flow.home.HomePresenter
import mx.com.wolf.shop.mvp.di.ActivityScope

/**
 * Created by Jose Barrera on 24/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
@Module
class HomeModule {
    @Provides
    @ActivityScope
    fun provideHomePresenter(itemRepository: ItemRepository): HomePresenter = HomePresenter(itemRepository)
}