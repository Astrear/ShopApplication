package mx.com.wolf.shop.di.module

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import mx.com.wolf.shop.data.di.Local
import mx.com.wolf.shop.data.di.Remote
import mx.com.wolf.shop.data.source.LoginApi
import mx.com.wolf.shop.data.source.local.ItemDAO
import mx.com.wolf.shop.data.source.local.ItemLocalDataSource
import mx.com.wolf.shop.data.source.local.ShopDatabase
import mx.com.wolf.shop.data.source.remote.ItemRemoteDataSource
import mx.com.wolf.shop.data.source.remote.ShopApi
import mx.com.wolf.shop.util.ApplicationExecutors
import retrofit2.Retrofit
import java.util.concurrent.Executors
import javax.inject.Singleton

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
@Module
class ItemRepositoryModule {

    @Provides
    @Singleton
    @Local
    fun provideItemLocalDataSource(executors: ApplicationExecutors, itemDAO: ItemDAO)
            : ItemLocalDataSource = ItemLocalDataSource(executors, itemDAO)

    @Provides
    @Singleton
    @Remote
    fun provideItemRemoteDataSource(shopApi: ShopApi): ItemRemoteDataSource = ItemRemoteDataSource(shopApi)

    @Provides
    @Singleton
    fun providesDatabase(context: Context)
            : ShopDatabase = Room.databaseBuilder(context.applicationContext, ShopDatabase::class.java, "Shop.db").build()

    @Provides
    @Singleton
    fun providesElementDAO(db: ShopDatabase): ItemDAO = db.itemDAO()

    @Provides
    @Singleton
    fun providesAppExecutors(): ApplicationExecutors = ApplicationExecutors(
            ApplicationExecutors.DiskIOThreadExecutor(),
            Executors.newFixedThreadPool(ApplicationExecutors.TREAD_LIMIT)
    )

    @Provides
    @Singleton
    fun provideShopApi(retrofit: Retrofit): ShopApi {
        return retrofit.create(ShopApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginApi(api: ShopApi): LoginApi = LoginApi(api)
}