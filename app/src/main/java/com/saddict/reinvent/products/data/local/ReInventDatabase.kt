package com.saddict.reinvent.products.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saddict.reinvent.products.model.local.ProductEntity

@Database(
    entities = [ ProductEntity::class ],
    version = 1,
    exportSchema = false
)
abstract class ReInventDatabase: RoomDatabase() {
    abstract fun reInventDao(): ReInventDao
}