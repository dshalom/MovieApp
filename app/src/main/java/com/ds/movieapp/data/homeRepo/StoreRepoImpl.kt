package com.ds.movieapp.data.homeRepo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface StoreRepo {
    suspend fun setSessionId(sessionId: String)
    suspend fun getSessionId(): String?
    suspend fun observeLoggedIn(): Flow<Boolean>
    suspend fun setBaseUrl(baseUrl: String)
    suspend fun getBaseUrl(): String?
    suspend fun logout()
}

class StoreRepoImpl @Inject constructor(@ApplicationContext val context: Context) : StoreRepo {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS)
    private val sessionIdKey: Preferences.Key<String> = stringPreferencesKey(SESSION_KEY)
    private val baseUrlKey: Preferences.Key<String> = stringPreferencesKey(BASE_URL_KEY)

    override suspend fun setSessionId(sessionId: String) {
        context.dataStore.edit { settings ->
            settings[sessionIdKey] = sessionId
        }
    }

    override suspend fun getSessionId(): String? {
        return context.dataStore
            .data.first()[sessionIdKey]
    }

    override suspend fun observeLoggedIn(): Flow<Boolean> {
        return context.dataStore.data.map {
            !it[sessionIdKey].isNullOrEmpty()
        }
    }

    override suspend fun setBaseUrl(baseUrl: String) {
        context.dataStore.edit { settings ->
            settings[baseUrlKey] = baseUrl
        }
    }

    override suspend fun getBaseUrl(): String? {
        return context.dataStore
            .data.first()[baseUrlKey]
    }

    override suspend fun logout() {
        context.dataStore.edit { settings ->
            settings[sessionIdKey] = ""
        }
    }

    companion object {
        private const val SETTINGS = "settings"
        private const val SESSION_KEY = "sessionKey"
        private const val BASE_URL_KEY = "baseUrlKey"
    }
}
