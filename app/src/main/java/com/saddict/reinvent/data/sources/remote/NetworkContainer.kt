package com.saddict.reinvent.data.sources.remote

import com.saddict.reinvent.data.sources.NetworkRepositoryInt
import com.saddict.reinvent.network.ReInventApiService
import com.saddict.reinvent.prop.Prop.Props.baseurl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

interface NetworkContainerInt {
    val networkRepository: NetworkRepositoryInt
}

object RequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        println("Outgoing request to ${request.url}")
        return chain.proceed(request)
    }
}

class NetworkContainer :NetworkContainerInt{
    private val okHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(RequestInterceptor)
        .build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(JacksonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl(baseurl)
        .build()
    private val retrofitService: ReInventApiService by lazy {
        retrofit.create(ReInventApiService::class.java)
    }
    override val networkRepository: NetworkRepositoryInt by lazy {
        NetworkRepository(retrofitService)
    }
}