package com.saddict.reinvent.network

import com.saddict.reinvent.model.remote.Product
import com.saddict.reinvent.model.remote.ProductPostRequest
import com.saddict.reinvent.model.remote.ProductResult
import com.saddict.reinvent.model.remote.RegisterUser
import com.saddict.reinvent.model.remote.RegisterUserResponse
import com.saddict.reinvent.model.remote.User
import com.saddict.reinvent.model.remote.UserResponse
import com.saddict.reinvent.utils.Constants.CREATE_USER_URL
import com.saddict.reinvent.utils.Constants.LOGIN_URL
import com.saddict.reinvent.utils.Constants.PRODUCTS_URL
import com.saddict.reinvent.utils.Constants.SINGLE_PRODUCTS_URL
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ReInventApiService {
    @GET(PRODUCTS_URL)
    suspend fun getProducts(@Query("format")format: String): Product

    @GET(SINGLE_PRODUCTS_URL)
    suspend fun getSingleProduct(@Path("id") id: Int) : Call<ProductResult>

    @POST(LOGIN_URL)
    suspend fun login(@Body user: User) : Response<UserResponse>

    @POST(PRODUCTS_URL)
    suspend fun postProducts(@Body body: ProductPostRequest): Response<ProductResult>

    @PUT(SINGLE_PRODUCTS_URL)
    suspend fun updateProduct(@Path("id") id: Int, @Body body: ProductPostRequest)
    : Response<ProductResult>

    @POST(CREATE_USER_URL)
    suspend fun register(@Body user: RegisterUser): Response<RegisterUserResponse>
}