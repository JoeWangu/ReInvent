package com.saddict.reinvent.ui.screens.productdetail

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.saddict.reinvent.data.sources.remote.NetworkContainer
import com.saddict.reinvent.model.remote.ProductPostRequest

class ProductEntryViewModel(context: Context): ViewModel() {
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
        if (validateInput()) {
            val response = apiRepo.postProducts(productEntryUiState.entryDetails.toProductPostRequest())
            if (response.isSuccessful) {
                val responseBody = response.body()
                Log.d("Success", "$responseBody")
            } else {
                val errorBody = response.errorBody()?.toString()
                Log.e("NotSent", "Error: $errorBody")
            }
        }
    }

    private fun validateInput(uiState: EntryDetails = productEntryUiState.entryDetails): Boolean {
        return with(uiState) {
            productName.isNotBlank() && modelNumber.isNotBlank() && specifications.isNotBlank() && price.isNotBlank()
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
    val price: String = ""
)

fun EntryDetails.toProductPostRequest(): ProductPostRequest = ProductPostRequest(
    productName = productName,
    modelNumber = modelNumber,
    specifications = specifications,
    price = price.toIntOrNull() ?: 0,
    image = 1,
    category = 1,
    supplier = 1
)

//fun EntryDetails.toProductsResult(): ProductResult = ProductResult(
//    id = 0,
//    name = productName,
//    modelNumber = modelNumber,
//    specifications = specifications,
//    price = price,
//    image = 1,
//    supplier = 1,
//    imageDetail = ImageDetail(id = 1, image = ""),
//    category = 1
//)