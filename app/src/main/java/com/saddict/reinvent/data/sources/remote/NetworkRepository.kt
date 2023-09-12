package com.saddict.reinvent.data.sources.remote

import com.saddict.reinvent.data.sources.NetworkRepositoryInt
import com.saddict.reinvent.model.remote.Product
import com.saddict.reinvent.model.remote.ProductPostRequest
import com.saddict.reinvent.model.remote.ProductResult
import com.saddict.reinvent.model.remote.User
import com.saddict.reinvent.model.remote.UserResponse
import com.saddict.reinvent.network.ReInventApiService
import retrofit2.Call
import retrofit2.Response

class NetworkRepository (private val reInventApiService: ReInventApiService)
    : NetworkRepositoryInt{
    override suspend fun getProducts(apiToken: String): Product =
        reInventApiService.getProducts("json", "Token $apiToken")
    override suspend fun postProducts(products: ProductPostRequest): Response<ProductResult> =
        reInventApiService.postProducts(products)
    override suspend fun getSingleProduct(id: Int, apiToken: String): Call<ProductResult> =
        reInventApiService.getSingleProduct(id = id,"Token $apiToken")
    override suspend fun updateProduct(id: Int, product: ProductPostRequest) =
        reInventApiService.updateProduct(id = id, body = product)
    override suspend fun login(user: User): Response<UserResponse> =
        reInventApiService.login(user)
}