package com.saddict.reinvent.network

import com.saddict.reinvent.model.remote.Product
import com.saddict.reinvent.model.remote.ProductPostRequest
import com.saddict.reinvent.model.remote.ProductResult
import com.saddict.reinvent.model.remote.User
import com.saddict.reinvent.model.remote.UserResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ReInventApiService {
    @GET("inventory/api/products/")
    suspend fun getProducts(@Query("format")format: String, @Header("Authorization") token: String): Product

    @GET("inventory/api/products/{id}/")
    suspend fun getSingleProduct(@Path("id") id: Int, @Header("Authorization") token: String) : Call<ProductResult>

    @Headers("Content-Type:application/json")
    @POST("login-api/")
    suspend fun login(@Body user: User) : Response<UserResponse>

    @Headers("Content-Type:application/json")
    @POST("inventory/api/products/")
    suspend fun postProducts(@Body body: ProductPostRequest): Response<ProductResult>
//
    @PUT("inventory/api/products/{id}/")
    suspend fun updateProduct(@Path("id") id: Int, @Body body: ProductPostRequest): Call<ProductResult>
}