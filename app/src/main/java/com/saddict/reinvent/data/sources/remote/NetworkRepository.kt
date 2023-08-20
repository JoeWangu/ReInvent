package com.saddict.reinvent.data.sources.remote

import com.saddict.reinvent.data.sources.NetworkRepositoryInt
import com.saddict.reinvent.model.remote.Product
import com.saddict.reinvent.network.ReInventApiService

class NetworkRepository (private val reInventApiService: ReInventApiService)
    : NetworkRepositoryInt{
    override suspend fun getProducts(): Product = reInventApiService.getProducts("json")
}