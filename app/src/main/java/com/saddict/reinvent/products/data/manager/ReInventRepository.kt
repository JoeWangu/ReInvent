package com.saddict.reinvent.products.data.manager

import android.content.Context
import android.util.Log
import com.saddict.reinvent.products.data.sources.DaoRepositoryInt
import com.saddict.reinvent.products.data.sources.NetworkRepositoryInt
import com.saddict.reinvent.products.data.sources.local.OfflineRepository
import com.saddict.reinvent.products.data.sources.local.ReInventDatabase
import com.saddict.reinvent.products.data.sources.remote.NetworkContainer
import com.saddict.reinvent.products.model.local.ProductEntity
import com.saddict.reinvent.utils.DataMapper.Companion.mapToEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.io.IOException

interface ReInventContainer{
    val daoRepositoryInt: DaoRepositoryInt
}

class ReInventRepository(
    private val context: Context,
    private val appApi: NetworkRepositoryInt = NetworkContainer(context).networkRepository,
    private val appDatabase: ReInventDatabase = ReInventDatabase.getDatabase(context)
): ReInventContainer {
    suspend fun fetchDataAndStore(): Flow<List<ProductEntity>> {
//        val preferenceDataStore = PreferenceDataStore(context)
//        preferenceDataStore.preferenceFlow.collect { token ->
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
//        }
        // Return products from the database
        return appDatabase.reInventDao().getAllProducts()
    }

    override val daoRepositoryInt: DaoRepositoryInt by lazy {
        OfflineRepository(appDatabase.reInventDao())
    }
}