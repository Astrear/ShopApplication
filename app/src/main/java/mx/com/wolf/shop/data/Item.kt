package mx.com.wolf.shop.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
@Entity(tableName = "item")
data class Item(
        @ColumnInfo(name = "name")
        var name: String,

        @ColumnInfo(name = "image")
        var image: String,

        @ColumnInfo(name = "description")
        var description: String?
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}