package com.saddict.reinvent.data.sources.remote

import com.saddict.reinvent.data.sources.NetworkRepositoryInt
import com.saddict.reinvent.network.ReInventApiService
import com.saddict.reinvent.prop.Prop.Props.baseurl
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

interface NetworkContainerInt {
    val networkRepository: NetworkRepositoryInt
}

class NetworkContainer :NetworkContainerInt{
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(JacksonConverterFactory.create())
        .baseUrl(baseurl)
        .build()
    private val retrofitService: ReInventApiService by lazy {
        retrofit.create(ReInventApiService::class.java)
    }
    override val networkRepository: NetworkRepositoryInt by lazy {
        NetworkRepository(retrofitService)
    }
}