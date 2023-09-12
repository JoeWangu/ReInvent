package com.saddict.reinvent.data.sources

import com.saddict.reinvent.model.local.ProductEntity
import com.saddict.reinvent.model.remote.Product
import com.saddict.reinvent.model.remote.ProductPostRequest
import com.saddict.reinvent.model.remote.ProductResult
import com.saddict.reinvent.model.remote.User
import com.saddict.reinvent.model.remote.UserResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response

interface NetworkRepositoryInt {
    suspend fun getProducts(apiToken: String): Product
    suspend fun postProducts(products: ProductPostRequest): Response<ProductResult>
    suspend fun getSingleProduct(id: Int, apiToken: String): Call<ProductResult>
    suspend fun updateProduct(id: Int, product: ProductPostRequest): Call<ProductResult>
    suspend fun login(user: User): Response<UserResponse>
}

interface DaoRepositoryInt {
    suspend fun insertAll(products: List<ProductEntity>)
    fun getAllProducts(): Flow<List<ProductEntity>>
    fun getProduct(id: Int): Flow<ProductEntity?>
}