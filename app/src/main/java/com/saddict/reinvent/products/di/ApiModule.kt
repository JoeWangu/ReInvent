package com.saddict.reinvent.products.di

import com.saddict.reinvent.products.data.manager.PreferenceDataStoreImpl
import com.saddict.reinvent.products.data.remote.remository.ApiRepository
import com.saddict.reinvent.products.data.remote.remository.ApiRepositoryImpl
import com.saddict.reinvent.products.network.ReInventApiService
import com.saddict.reinvent.prop.Prop.Props.baseurl
import com.saddict.reinvent.utils.Constants.CREATE_USER_URL
import com.saddict.reinvent.utils.Constants.LOGIN_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun providesProductApi(
        preferenceDataStoreImpl: PreferenceDataStoreImpl
    ): ReInventApiService {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val requestInterceptor = Interceptor.invoke { chain ->
            val request = chain.request()
            val token = preferenceDataStoreImpl.getToken()
            println("Outgoing request to ${request.url}")
            println("Token is $token")
            return@invoke if (
                !request.url.encodedPath.contains(LOGIN_URL) &&
                !request.url.encodedPath.contains(CREATE_USER_URL)
            ){
                val requestBuild = request.newBuilder()
                    .addHeader("Authorization", "Token $token")
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(requestBuild)
            }else{
                val requestBuild = request.newBuilder().build()
                chain.proceed(requestBuild)
            }
        }
        val okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(requestInterceptor)
            .build()
        return Retrofit
            .Builder()
            .baseUrl(baseurl)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ReInventApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesApiRepositoryImpl(productApi: ReInventApiService): ApiRepository {
        return ApiRepositoryImpl(productApi)
    }
}