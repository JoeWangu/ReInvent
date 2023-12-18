package com.saddict.reinvent.products.model.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManagerInt {
    suspend fun setToken(token: String?)
    val preferenceFlow: Flow<String>
}