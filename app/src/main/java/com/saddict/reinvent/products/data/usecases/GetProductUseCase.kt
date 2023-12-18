package com.saddict.reinvent.products.data.usecases

import com.saddict.reinvent.products.data.local.locasitory.LocalDataSource
import com.saddict.reinvent.products.model.local.ProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductUseCase @Inject constructor(private val appRepository: LocalDataSource) {
    fun getProduct(id: Int): Flow<ProductEntity?> {
        return appRepository.fetchOneProduct(id)
    }
}