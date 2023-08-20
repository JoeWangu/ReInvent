package com.saddict.reinvent.data.sources.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.saddict.reinvent.data.ReInventDao
import com.saddict.reinvent.model.local.ProductEntity

@Database(entities = [ ProductEntity::class ], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class ReInventDatabase: RoomDatabase() {
    abstract fun reInventDao(): ReInventDao
    companion object{
        @Volatile
        private var Instance: ReInventDatabase? = null

        fun getDatabase(context: Context): ReInventDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, ReInventDatabase::class.java, "reinvent_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}