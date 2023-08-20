package com.saddict.reinvent.data.sources

import com.saddict.reinvent.model.local.ProductEntity
import com.saddict.reinvent.model.remote.Product
import kotlinx.coroutines.flow.Flow

interface NetworkRepositoryInt {
    suspend fun getProducts(): Product
}

interface DaoRepositoryInt {
    suspend fun insertAll(products: List<ProductEntity>)
    fun getAllProducts(): Flow<List<ProductEntity>>
    fun getProduct(id: Int): Flow<ProductEntity?>
}