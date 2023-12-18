package com.saddict.reinvent.products.model.local

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "product_name")
    val productName: String,
    @ColumnInfo(name = "model_number")
    val modelNumber: String,
    val specifications: String,
    val price: Double,
    val image: Int,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    val category: Int,
    val supplier: Int,
)