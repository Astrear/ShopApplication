package mx.com.wolf.shop.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import mx.com.wolf.shop.data.source.ItemRepository
import mx.com.wolf.shop.util.ViewModelFactory
import mx.com.wolf.shop.util.ViewModelKey

/**
 * Created by Jose Barrera on 25/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ItemRepository::class)
    internal abstract fun postListViewModel(viewModel: ItemRepository): ViewModel

    //Add more ViewModels here
}