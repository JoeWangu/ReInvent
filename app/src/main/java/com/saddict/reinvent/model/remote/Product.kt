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
    val id: Int? = null,
    @JsonProperty("name")
    val name: String? = null,
    @JsonProperty("model_number")
    val modelNumber: String? = null,
    @JsonProperty("specifications")
    val specifications: String? = null,
    @JsonProperty("price")
    val price: String? = null,
    @JsonProperty("image")
    val image: Int? = null,
    @JsonProperty("image_detail")
    val imageDetail: ImageDetail? = null,
    @JsonProperty("category")
    val category: Int? = null,
    @JsonProperty("supplier")
    val supplier: Int? = null
)

@Keep
data class ImageDetail(
    @JsonProperty("id")
    val id: Int? = null,
    @JsonProperty("image")
    val image: String? = null
)