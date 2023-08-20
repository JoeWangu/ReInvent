package com.saddict.reinvent.data

import android.content.Context
import android.util.Log
import com.saddict.reinvent.data.sources.DaoRepositoryInt
import com.saddict.reinvent.data.sources.NetworkRepositoryInt
import com.saddict.reinvent.data.sources.local.OfflineRepository
import com.saddict.reinvent.data.sources.local.ReInventDatabase
import com.saddict.reinvent.data.sources.remote.NetworkContainer
import com.saddict.reinvent.model.local.ProductEntity
import com.saddict.reinvent.utils.DataMapper.Companion.mapToEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.io.IOException

interface ReInventContainer{
    val daoRepositoryInt: DaoRepositoryInt
}

class ReInventRepository(
    private val context: Context,
    private val appApi: NetworkRepositoryInt = NetworkContainer().networkRepository,
    private val appDatabase: ReInventDatabase = ReInventDatabase.getDatabase(context)
): ReInventContainer {
    suspend fun fetchDataAndStore(): Flow<List<ProductEntity>> {
        val productsInDb = appDatabase.reInventDao().getAllProducts().first()
        if (productsInDb.isEmpty()) {
            try {
                val response = appApi.getProducts()
                val entities = response.results?.map { it?.let { it1 -> mapToEntity(it1) } }
                entities.let { products ->
                    if (products != null) {
                        appDatabase.reInventDao().insertAll(products)
                    }
                }
            } catch (e: IOException) {
                // Log the error
                Log.e("AppRepository", "Error fetching data", e)
            }
        }
        // Return products from the database
        return appDatabase.reInventDao().getAllProducts()
    }
    suspend fun refreshDatabase() {
        try {
            val response = appApi.getProducts()
            val entities = response.results?.map { mapToEntity(it!!) }
            entities.let { products ->
                appDatabase.reInventDao().insertAll(products)
            }
        } catch (e: IOException) {
            // Log the error
            Log.e("AppRepository", "Error fetching data", e)
        }
    }
    override val daoRepositoryInt: DaoRepositoryInt by lazy {
        OfflineRepository(appDatabase.reInventDao())
    }
}