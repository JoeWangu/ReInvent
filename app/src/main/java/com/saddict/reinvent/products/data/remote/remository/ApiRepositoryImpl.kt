package com.saddict.reinvent.products.data.remote.remository

import com.saddict.reinvent.products.model.remote.PostProduct
import com.saddict.reinvent.products.model.remote.Product
import com.saddict.reinvent.products.model.remote.ProductResults
import com.saddict.reinvent.products.model.remote.RegisterUser
import com.saddict.reinvent.products.model.remote.RegisterUserResponse
import com.saddict.reinvent.products.model.remote.User
import com.saddict.reinvent.products.model.remote.UserResponse
import com.saddict.reinvent.products.network.ReInventApiService
import retrofit2.Response
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val productApi: ReInventApiService
): ApiRepository {
    override suspend fun getProducts(page: Int): Product {
        return productApi.getProducts(format = "json", page)
    }

    override suspend fun getSingleProduct(id: Int): ProductResults {
        return productApi.getSingleProduct(id)
    }

    override suspend fun postProducts(body: PostProduct): Response<ProductResults> {
        return productApi.postProducts(body)
    }

    override suspend fun updateProduct(id: Int, body: PostProduct): Response<ProductResults> {
        return productApi.updateProduct(id, body)
    }

    override suspend fun register(user: RegisterUser): Response<RegisterUserResponse> {
        return productApi.register(user)
    }

    override suspend fun login(user: User): Response<UserResponse> {
        return productApi.login(user)
    }
}