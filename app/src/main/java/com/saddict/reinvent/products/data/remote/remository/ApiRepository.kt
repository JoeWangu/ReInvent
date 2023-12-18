package com.saddict.reinvent.products.data.remote.remository

import com.saddict.reinvent.products.model.remote.PostProduct
import com.saddict.reinvent.products.model.remote.Product
import com.saddict.reinvent.products.model.remote.ProductResults
import com.saddict.reinvent.products.model.remote.RegisterUser
import com.saddict.reinvent.products.model.remote.RegisterUserResponse
import com.saddict.reinvent.products.model.remote.User
import com.saddict.reinvent.products.model.remote.UserResponse
import retrofit2.Response

interface ApiRepository {
    suspend fun getProducts(page: Int): Product

    suspend fun getSingleProduct(id: Int): ProductResults

    suspend fun postProducts(body: PostProduct): Response<ProductResults>

    suspend fun updateProduct(id: Int, body: PostProduct): Response<ProductResults>

    suspend fun register(user: RegisterUser): Response<RegisterUserResponse>

    suspend fun login(user: User): Response<UserResponse>
}