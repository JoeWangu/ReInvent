package com.saddict.reinvent.products.data.local.locasitory

import com.saddict.reinvent.products.data.local.ReInventDao
import com.saddict.reinvent.products.model.local.ProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val reInventDao: ReInventDao
) : LocalDataSource {
    override suspend fun upsertAllProducts(products: List<ProductEntity>) {
        return reInventDao.upsertAllProducts(products)
    }

    override suspend fun fetchAllProducts(): Flow<List<ProductEntity>> {
        return reInventDao.fetchAllProducts()
    }

    override fun fetchAllProductsDesc(): Flow<List<ProductEntity>> {
        return reInventDao.fetchAllProductsDesc()
    }

    override fun fetchOneProduct(id: Int): Flow<ProductEntity> {
        return reInventDao.fetchOneProduct(id)
    }
}