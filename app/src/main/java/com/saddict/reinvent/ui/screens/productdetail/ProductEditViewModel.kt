package com.saddict.reinvent.ui.screens.productdetail

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.reinvent.data.sources.DaoRepositoryInt
import com.saddict.reinvent.data.sources.remote.NetworkContainer
import com.saddict.reinvent.model.local.ProductEntity
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProductEditViewModel(
    context: Context,
    savedStateHandle: SavedStateHandle,
    private val repository: DaoRepositoryInt
) : ViewModel() {
    private val apiRepo = NetworkContainer(context).networkRepository
    var productEditUiState by mutableStateOf(ProductEntryUiState())
        private set
    private val productId: Int = checkNotNull(savedStateHandle[ProductEditDestination.productIdArg])

    fun updateUiState(entryDetails: EntryDetails) {
        productEditUiState = ProductEntryUiState(
            entryDetails = entryDetails,
            isEntryValid = validateInput(entryDetails)
        )
    }

    init {
        viewModelScope.launch {
            productEditUiState = repository.getProduct(productId)
                .filterNotNull()
                .first()
                .toProductEditUiState(true)
        }
    }

    suspend fun updateProduct() {
        if (validateInput(productEditUiState.entryDetails)) {
            apiRepo.updateProduct(
                id = productId,
                product = productEditUiState.entryDetails.toProductPostRequest()
            )
//            repository.updateItem(itemUiState.itemDetails.toItem())
        }
    }

    private fun validateInput(uiState: EntryDetails = productEditUiState.entryDetails): Boolean {
        return with(uiState) {
            productName.isNotBlank() && modelNumber.isNotBlank() && specifications.isNotBlank() && price.isNotBlank()
        }
    }
}

fun ProductEntity.toEditDetails(): EntryDetails = EntryDetails(
    productName = productName,
    modelNumber = modelNumber,
    specifications = specifications,
    price = price
)

fun ProductEntity.toProductEditUiState(isEntryValid: Boolean = false):
        ProductEntryUiState = ProductEntryUiState(
    isEntryValid = isEntryValid,
    entryDetails = this.toEditDetails()
)