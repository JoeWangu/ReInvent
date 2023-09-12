package com.saddict.reinvent.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.reinvent.data.sources.remote.NetworkContainer
import com.saddict.reinvent.model.remote.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException

sealed interface LoginUiState {
    data object Success : LoginUiState
    data object Error : LoginUiState
    data object Loading : LoginUiState
}

class LoginViewModel : ViewModel() {
    private val _uiState = MutableSharedFlow<LoginUiState>()
    val uiState: SharedFlow<LoginUiState> = _uiState
    private val repository = NetworkContainer().networkRepository

    fun login(username: String, password: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _uiState.emit(LoginUiState.Loading)
                    val user = User(password = password, username = username)
                    val login = repository.login(user)
                    if (login.isSuccessful) {
                        _uiState.emit(LoginUiState.Success)
                    } else {
                        _uiState.emit(LoginUiState.Error)
                    }
                } catch (e: IOException) {
                    Log.e("LoginError", "Logging in error $e")
                }
            }
        }
    }
}