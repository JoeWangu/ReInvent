package com.saddict.reinvent.ui.screens.productdetail

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.reinvent.data.manager.AppUiState
import com.saddict.reinvent.data.sources.remote.NetworkContainer
import com.saddict.reinvent.model.remote.ProductPostRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException

class ProductEntryViewModel(context: Context): ViewModel() {
    private val _uiState = MutableSharedFlow<AppUiState>()
    val uiState: SharedFlow<AppUiState> = _uiState
    private val apiRepo = NetworkContainer(context).networkRepository
    var productEntryUiState by mutableStateOf(ProductEntryUiState())
        private set

    fun updateUiState(entryDetails: EntryDetails) {
        productEntryUiState =
            ProductEntryUiState(
                entryDetails = entryDetails,
                isEntryValid = validateInput(entryDetails)
            )
    }

    suspend fun saveProduct() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    if (validateInput()) {
                        _uiState.emit(AppUiState.Loading)
                        val response =
                            apiRepo.postProducts(productEntryUiState.entryDetails.toProductPostRequest())
                        if (response.isSuccessful) {
                            val responseBody = response.message()
                            Log.d("Success", responseBody)
                            _uiState.emit(AppUiState.Success)
                        } else {
                            val errorBody = response.errorBody()?.toString()
                            Log.e("DataNotSent", "Error: $errorBody")
                            _uiState.emit(AppUiState.Error)
                        }
                    }
                }catch (e: IOException){
                    Log.e("ErrorSendingData", "Error: $e")
                }
            }
        }
    }

    private fun validateInput(uiState: EntryDetails = productEntryUiState.entryDetails): Boolean {
        return with(uiState) {
            productName.isNotBlank() && modelNumber.isNotBlank()
                    && specifications.isNotBlank() && price.isNotBlank()
        }
    }
}

data class ProductEntryUiState(
    val entryDetails: EntryDetails = EntryDetails(),
    val isEntryValid: Boolean = false
)

data class EntryDetails(
    val productName: String = "",
    val modelNumber: String = "",
    val specifications: String = "",
    val price: String = "",
    val image: String = "1",
    val category: String = "1",
    val supplier: String = "1"
)

fun EntryDetails.toProductPostRequest(): ProductPostRequest = ProductPostRequest(
    name = productName,
    model_number = modelNumber,
    specifications = specifications,
    price = price.toDoubleOrNull() ?: 0.0,
    image = image.toIntOrNull() ?: 1,
    category = category.toIntOrNull() ?: 1,
    supplier = supplier.toIntOrNull() ?: 1
)