package com.saddict.reinvent.products.data.sources.remote

import com.saddict.reinvent.products.data.sources.NetworkRepositoryInt
import com.saddict.reinvent.products.model.remote.Product
import com.saddict.reinvent.products.model.remote.ProductPostRequest
import com.saddict.reinvent.products.model.remote.ProductResult
import com.saddict.reinvent.products.model.remote.RegisterUser
import com.saddict.reinvent.products.model.remote.RegisterUserResponse
import com.saddict.reinvent.products.model.remote.User
import com.saddict.reinvent.products.model.remote.UserResponse
import com.saddict.reinvent.products.network.ReInventApiService
import retrofit2.Call
import retrofit2.Response

class NetworkRepository (private val reInventApiService: ReInventApiService)
    : NetworkRepositoryInt {
    override suspend fun getProducts(): Product =
        reInventApiService.getProducts("json")
    override suspend fun postProducts(products: ProductPostRequest): Response<ProductResult> =
        reInventApiService.postProducts(products)
    override suspend fun getSingleProduct(id: Int): Call<ProductResult> =
        reInventApiService.getSingleProduct(id = id)
    override suspend fun updateProduct(id: Int, product: ProductPostRequest)
    : Response<ProductResult> = reInventApiService.updateProduct(id = id, body = product)
    override suspend fun login(user: User): Response<UserResponse> =
        reInventApiService.login(user)

    override suspend fun register(user: RegisterUser)
    : Response<RegisterUserResponse> = reInventApiService.register(user)
}