package com.saddict.reinvent.network

import com.saddict.reinvent.model.remote.Product
import retrofit2.http.GET
import retrofit2.http.Query

interface ReInventApiService {
    @GET("inventory/api/products/")
    suspend fun getProducts(@Query("format")format: String): Product

}