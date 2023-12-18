package com.saddict.reinvent.products.data.manager

sealed interface AppUiState{
    data object Success: AppUiState
    data object Error: AppUiState
    data object Loading: AppUiState
}