package com.saddict.reinvent.utils

import com.saddict.reinvent.model.local.ProductEntity
import com.saddict.reinvent.model.remote.ProductResult

class DataMapper {
    companion object{
        fun mapToEntity(productsResult: ProductResult): ProductEntity {
            return ProductEntity(
                id = productsResult.id,
                productName = productsResult.name,
                modelNumber = productsResult.modelNumber,
                specifications = productsResult.specifications,
                price = productsResult.price,
                image = productsResult.image,
                imageUrl = productsResult.imageDetail?.image,
                category = productsResult.category,
                supplier = productsResult.supplier
            )
        }
    }
}