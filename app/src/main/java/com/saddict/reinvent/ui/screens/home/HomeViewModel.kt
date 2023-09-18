package com.saddict.reinvent.ui.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.reinvent.data.manager.ReInventRepository
import com.saddict.reinvent.data.sources.DaoRepositoryInt
import com.saddict.reinvent.model.local.ProductEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed interface HomeUiState{
    data class Success(val products: List<ProductEntity?> = listOf()) : HomeUiState
//    data object Error : HomeUiState
    data object Loading : HomeUiState
}

class HomeViewModel(repository: DaoRepositoryInt, context: Context)
    : ViewModel(){
    private val repo = ReInventRepository(context)
    init {
        runRepo()
    }

    private fun runRepo() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.fetchDataAndStore()
            }
        }
    }

    val homeUiState: StateFlow<HomeUiState> = repository.getAllProducts().map {
        HomeUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = HomeUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000L)
    )
}