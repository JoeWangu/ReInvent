package com.saddict.reinvent.products.di

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.saddict.reinvent.products.data.local.ReInventDatabase
import com.saddict.reinvent.products.data.manager.CustomPagingSource
import com.saddict.reinvent.products.data.remote.remository.ApiRepository
import com.saddict.reinvent.products.model.local.ProductEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PagingModule {
    @Provides
    @Singleton
    fun providePager(
        productApi: ApiRepository,
        appDatabase: ReInventDatabase
    ): Pager<Int, ProductEntity> {
        return Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 1),
            pagingSourceFactory = { CustomPagingSource(productApi, appDatabase) }
        )
    }
}