package com.saddict.reinvent.data.sources.remote

import android.content.Context
import com.saddict.reinvent.data.manager.PreferenceDataStore
import com.saddict.reinvent.data.sources.NetworkRepositoryInt
import com.saddict.reinvent.network.ReInventApiService
import com.saddict.reinvent.prop.Prop.Props.baseurl
import com.saddict.reinvent.utils.Constants.LOGIN_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

interface NetworkContainerInt {
    val networkRepository: NetworkRepositoryInt
}

class RequestInterceptor(context: Context) : Interceptor {
    private val preferenceDataStore = PreferenceDataStore(context)

    //    private val token = preferenceDataStore.preferenceFlow.map { it }
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token = preferenceDataStore.getToken()
        println("Outgoing request to ${request.url}")
        return if (!request.url.encodedPath.contains(LOGIN_URL)) {
            val requestBuild = request.newBuilder()
                .header("Authorization", "Token $token")
                .header("Content-Type", "application/json")
                .build()
            chain.proceed(requestBuild)
        } else {
            val requestBuild = request.newBuilder().build()
            chain.proceed(requestBuild)
        }
    }
}

class NetworkContainer(context: Context) :NetworkContainerInt{
    private val bodyInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    private val okHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(RequestInterceptor(context))
        .addInterceptor(bodyInterceptor)
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