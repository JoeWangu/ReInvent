package com.saddict.reinvent.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.saddict.reinvent.model.manager.LocalUserManagerInt
import com.saddict.reinvent.utils.Constants.TOKEN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

//private const val TOKEN = "user_token"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = TOKEN
)
class PreferenceDataStore(context: Context)
    : LocalUserManagerInt {
    private val pref = context.dataStore

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

    override suspend fun setToken(token: String){
        pref.edit { preferences ->
            preferences[PreferencesKeys.TOKEN_KEY] = token
        }
    }
}