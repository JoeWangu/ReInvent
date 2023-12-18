package com.saddict.reinvent.products.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.saddict.reinvent.products.model.manager.LocalUserManagerInt
import com.saddict.reinvent.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException

private val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(
    name = Constants.TOKEN
)
class PreferenceDataStore(context: Context)
    : LocalUserManagerInt {
    private val pref = context.tokenDataStore

    private object PreferencesKeys {
        val TOKEN_KEY = stringPreferencesKey("token_key")
    }

    override val preferenceFlow: Flow<String> = pref.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.TOKEN_KEY] ?: ""
        }

    fun getToken(): String {
        var token: String
        runBlocking {
            token = preferenceFlow.first()
        }
        return token
    }

    override suspend fun setToken(token: String?){
        pref.edit { preferences ->
            preferences[PreferencesKeys.TOKEN_KEY] = token ?: ""
        }
    }
}