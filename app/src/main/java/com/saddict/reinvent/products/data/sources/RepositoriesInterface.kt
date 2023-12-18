package com.saddict.reinvent.products.data.sources

import com.saddict.reinvent.products.model.local.ProductEntity
import com.saddict.reinvent.products.model.remote.Product
import com.saddict.reinvent.products.model.remote.ProductPostRequest
import com.saddict.reinvent.products.model.remote.ProductResult
import com.saddict.reinvent.products.model.remote.RegisterUser
import com.saddict.reinvent.products.model.remote.RegisterUserResponse
import com.saddict.reinvent.products.model.remote.User
import com.saddict.reinvent.products.model.remote.UserResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response

interface NetworkRepositoryInt {
    suspend fun getProducts(): Product
    suspend fun getSingleProduct(id: Int): Call<ProductResult>
    suspend fun postProducts(products: ProductPostRequest): Response<ProductResult>
    suspend fun updateProduct(id: Int, product: ProductPostRequest): Response<ProductResult>
    suspend fun login(user: User): Response<UserResponse>
    suspend fun register(user: RegisterUser): Response<RegisterUserResponse>
}

interface DaoRepositoryInt {
    suspend fun insertAll(products: List<ProductEntity>)
    fun getAllProducts(): Flow<List<ProductEntity>>
    fun getProduct(id: Int): Flow<ProductEntity?>
}