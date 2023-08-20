package com.saddict.reinvent.data.sources.local

import com.saddict.reinvent.data.ReInventDao
import com.saddict.reinvent.data.sources.DaoRepositoryInt
import com.saddict.reinvent.model.local.ProductEntity
import kotlinx.coroutines.flow.Flow

class OfflineRepository(private val reInventDao: ReInventDao)
    : DaoRepositoryInt {
    override suspend fun insertAll(products: List<ProductEntity>) = reInventDao.insertAll(products)
    override fun getAllProducts(): Flow<List<ProductEntity>> = reInventDao.getAllProducts()
    override fun getProduct(id: Int): Flow<ProductEntity?> = reInventDao.getProduct(id)
}