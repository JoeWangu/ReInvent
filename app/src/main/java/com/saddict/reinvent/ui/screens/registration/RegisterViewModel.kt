package com.saddict.reinvent.ui.screens.registration

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.reinvent.products.data.manager.AppUiState
import com.saddict.reinvent.products.data.manager.PreferenceDataStore
import com.saddict.reinvent.products.data.sources.remote.NetworkContainer
import com.saddict.reinvent.products.model.remote.RegisterUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException

class RegisterViewModel(context: Context) : ViewModel() {
    private val _uiState = MutableSharedFlow<AppUiState>()
    val uiState: SharedFlow<AppUiState> = _uiState
    private val repository = NetworkContainer(context).networkRepository
    private val userPreferenceFlow = PreferenceDataStore(context)

    fun register(
        username: String,
        password: String,
        email: String
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _uiState.emit(AppUiState.Loading)
                    val user = RegisterUser(
                        password = password, username = username, email = email
                    )
                    val register = repository.register(user)
                    if (register.isSuccessful) {
                        val responseBody = register.body()
                        val token = responseBody!!.token
                        userPreferenceFlow.setToken(token)
                        Log.d("Success", "Success Log")
                        _uiState.emit(AppUiState.Success)
                    } else {
                        _uiState.emit(AppUiState.Error)
                        val errorBody = register.raw()
                        Log.e("NotSent", "Error: $errorBody")
                    }
                } catch (e: IOException) {
                    Log.e("RegisterError", "Logging in error $e")
                }
            }
        }
    }
}