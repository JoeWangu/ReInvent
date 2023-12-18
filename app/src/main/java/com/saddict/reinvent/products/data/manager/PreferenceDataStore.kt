package com.saddict.reinvent.products.data.manager

interface PreferenceDataStore {
    fun getToken(): String
    suspend fun setToken(token: String?)
}