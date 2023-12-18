package com.saddict.reinvent.products.di

import android.content.Context
import androidx.room.Room
import com.saddict.reinvent.products.data.local.ReInventDao
import com.saddict.reinvent.products.data.local.ReInventDatabase
import com.saddict.reinvent.products.data.local.locasitory.LocalDataSource
import com.saddict.reinvent.products.data.local.locasitory.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ReInventDatabase{
        return Room.databaseBuilder(context, ReInventDatabase::class.java, "app_database")
            .build()
    }
    @Provides
    @Singleton
    fun provideProductDao(appDatabase: ReInventDatabase): ReInventDao {
        return appDatabase.reInventDao()
    }
    @Provides
    @Singleton
    fun provideLocalDataSource(productDao: ReInventDao): LocalDataSource {
        return LocalDataSourceImpl(productDao)
    }
}