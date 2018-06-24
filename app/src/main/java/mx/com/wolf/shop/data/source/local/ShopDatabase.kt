package mx.com.wolf.shop.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import mx.com.wolf.shop.data.Item

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ShopDatabase: RoomDatabase() {
    abstract fun itemDAO(): ItemDAO
}