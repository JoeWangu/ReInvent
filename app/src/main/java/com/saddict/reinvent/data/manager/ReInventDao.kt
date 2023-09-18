package com.saddict.reinvent.data.manager

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saddict.reinvent.model.local.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReInventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(product: List<ProductEntity?>?)

    @Query("SELECT * from products WHERE id = :id")
    fun getProduct(id: Int): Flow<ProductEntity>

    @Query("SELECT * from products ORDER BY id ASC")
    fun getAllProducts(): Flow<List<ProductEntity>>
}