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
import timber.log.Timber
import javax.inject.Inject

interface SessionRepo {
    suspend fun storeSessionId(sessionId: String)
    suspend fun getSessionId(): String?
    suspend fun observeLoggedIn(): Flow<Boolean>
    suspend fun logout()
}

class SessionRepoImpl @Inject constructor(@ApplicationContext val context: Context) : SessionRepo {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS)
    private val sessionIdKey: Preferences.Key<String> = stringPreferencesKey(SESSION_KEY)

    override suspend fun storeSessionId(sessionId: String) {
        context.dataStore.edit { settings ->
            Timber.i("setting session to $sessionId")

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

    override suspend fun logout() {
        context.dataStore.edit { settings ->
            settings[sessionIdKey] = ""
        }
    }

    companion object {
        private const val SETTINGS = "settings"
        private const val SESSION_KEY = "sessionKey"
    }
}
