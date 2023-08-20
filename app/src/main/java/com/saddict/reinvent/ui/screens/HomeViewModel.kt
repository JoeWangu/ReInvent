package com.saddict.reinvent.ui.screens

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.saddict.reinvent.ReInventApplication
import com.saddict.reinvent.data.ReInventRepository
import com.saddict.reinvent.data.sources.DaoRepositoryInt
import com.saddict.reinvent.model.local.ProductEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

sealed interface HomeUiState{
    data class Success(val products: List<ProductEntity?> = listOf()) : HomeUiState
    data object Error : HomeUiState
    data object Loading : HomeUiState
}

class HomeViewModel(private val repository: DaoRepositoryInt, context: Context)
    : ViewModel(){
    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
    private val repo = ReInventRepository(context)
    init {
        runRepo()
        getProducts()
    }

    private fun runRepo() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.fetchDataAndStore()
            }
        }
    }

    fun getProducts() {
        viewModelScope.launch {
            homeUiState = HomeUiState.Loading
            try {
                repository.getAllProducts().collect { products ->
                    withContext(Dispatchers.IO) {
                        homeUiState = HomeUiState.Success(products)
                    }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.IO) {
                    homeUiState = HomeUiState.Error
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.IO) {
                    homeUiState = HomeUiState.Error
                }
            }
        }
    }

    fun refreshDb() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.refreshDatabase()
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ReInventApplication)
                val productsRepository = application.container.daoRepositoryInt
                HomeViewModel(
                    repository = productsRepository,
                    context = application.applicationContext
                )
            }
        }
    }
}