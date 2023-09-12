package com.saddict.reinvent.ui.screens.productdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.reinvent.data.sources.DaoRepositoryInt
import com.saddict.reinvent.model.local.ProductEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ProductDetails(
    val id: Int = 0,
    val productName: String = "",
    val modelNumber: String = "",
    val specifications: String = "",
    val price: String = "",
    val image: Int = 0,
    val imageUrl: String = "",
    val category: String = "",
    val supplier: String = "",
)

data class ProductDetailsUiState(
    val productDetails: ProductDetails = ProductDetails()
)

class ProductDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    repository: DaoRepositoryInt
): ViewModel() {
    private val productId: Int = checkNotNull(savedStateHandle[ProductDetailsDestination.productIdArg])
    val uiState: StateFlow<ProductDetailsUiState> =
        repository.getProduct(productId)
            .filterNotNull()
            .map { ProductDetailsUiState(productDetails = it.toProductDetails()) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ProductDetailsUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

fun ProductEntity.toProductDetails(): ProductDetails = ProductDetails(
    id = id,
    productName = productName,
    modelNumber = modelNumber,
    specifications = specifications,
    price = price,
    imageUrl = imageUrl,
    category = category.toString(),
    supplier = supplier.toString()
)

fun ProductDetails.toProductEntity(): ProductEntity = ProductEntity(
    id = id,
    productName = productName,
    modelNumber = modelNumber,
    specifications = specifications,
    price = price,
    image = image,
    category = category.toIntOrNull() ?: 0,
    supplier = supplier.toIntOrNull() ?: 0,
    imageUrl = imageUrl
)