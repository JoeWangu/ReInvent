package com.saddict.reinvent.model.remote


import com.fasterxml.jackson.annotation.JsonProperty
import androidx.annotation.Keep

@Keep
data class Product(
    @JsonProperty("count")
    val count: Int? = null,
    @JsonProperty("next")
    val next: String? = null,
    @JsonProperty("previous")
    val previous: String? = null,
    @JsonProperty("results")
    val results: List<ProductResult?>? = listOf()
)

@Keep
data class ProductResult(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("model_number")
    val modelNumber: String,
    @JsonProperty("specifications")
    val specifications: String,
    @JsonProperty("price")
    val price: Double,
    @JsonProperty("image")
    val image: Int,
    @JsonProperty("image_detail")
    val imageDetail: ImageDetail,
    @JsonProperty("category")
    val category: Int,
    @JsonProperty("supplier")
    val supplier: Int
)

@Keep
data class ImageDetail(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("image")
    val image: String
)

@Keep
data class ProductPostRequest(
    val name: String,
    @Suppress("PropertyName")
    val model_number: String,
    val specifications: String,
    val price: Double,
    val image: Int,
    val category: Int,
    val supplier: Int,
)