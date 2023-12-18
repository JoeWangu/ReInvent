package com.saddict.reinvent.ui.screens.productdetail

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.reinvent.products.data.manager.AppUiState
import com.saddict.reinvent.products.data.sources.DaoRepositoryInt
import com.saddict.reinvent.products.data.sources.remote.NetworkContainer
import com.saddict.reinvent.products.model.local.ProductEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException

class ProductEditViewModel(
    context: Context,
    savedStateHandle: SavedStateHandle,
    private val repository: DaoRepositoryInt
) : ViewModel() {
    private val apiRepo = NetworkContainer(context).networkRepository
    var productEditUiState by mutableStateOf(ProductEntryUiState())
        private set
    private val productId: Int = checkNotNull(savedStateHandle[ProductEditDestination.productIdArg])
    private val _uiState = MutableSharedFlow<AppUiState>()
    val uiState: SharedFlow<AppUiState> = _uiState

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
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _uiState.emit(AppUiState.Loading)
                    if (validateInput(productEditUiState.entryDetails)) {
                        val updateResponse = apiRepo.updateProduct(
                            id = productId,
                            product = productEditUiState.entryDetails.toProductPostRequest()
                        )
                        if (updateResponse.isSuccessful) {
                            val responseBody = updateResponse.message()
                            Log.d("Success", responseBody)
                            _uiState.emit(AppUiState.Success)
                        } else {
                            val errorBody = updateResponse.raw()
                            Log.e("NotSent", "Error: $errorBody")
                            _uiState.emit(AppUiState.Error)
                        }
                    }
                } catch (e: IOException) {
                    Log.e("Did not update", "Error: $e")
                }
            }
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
    price = price.toString(),
    image = image.toString(),
    category = category.toString(),
    supplier = supplier.toString()
)

fun ProductEntity.toProductEditUiState(isEntryValid: Boolean = false):
        ProductEntryUiState = ProductEntryUiState(
    isEntryValid = isEntryValid,
    entryDetails = this.toEditDetails()
)