package com.saddict.reinvent.utils

import com.saddict.reinvent.products.model.local.ProductEntity
import com.saddict.reinvent.products.model.remote.ProductImageDetails
import com.saddict.reinvent.products.model.remote.ProductResults

class DataMapper {
    companion object{
        fun ProductResults.mapToEntity(): ProductEntity {
            return ProductEntity(
                id = id,
                productName = productName,
                modelNumber = modelNumber,
                specifications = modelNumber,
                price = price,
                image = image,
                imageDetails = imageDetails.image,
                category = category,
                supplier = supplier
            )
        }
        fun ProductEntity.mapToResults(): ProductResults{
            return ProductResults(
                id = id,
                productName = productName,
                modelNumber = modelNumber,
                specifications = modelNumber,
                price = price,
                image = image,
                imageDetails = ProductImageDetails(image, imageDetails),
                category = category,
                supplier = supplier
            )
        }
    }
}