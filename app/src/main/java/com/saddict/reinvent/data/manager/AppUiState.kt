package com.saddict.reinvent.data.manager

sealed interface AppUiState{
    data object Success: AppUiState
    data object Error: AppUiState
    data object Loading: AppUiState
}