package com.ds.movieapp.data.repo.homeRepo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface StoreRepo {
    suspend fun setBaseUrl(baseUrl: String)
    suspend fun getBaseUrl(): String?
}

class StoreRepoImpl @Inject constructor(@ApplicationContext val context: Context) : StoreRepo {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS)
    private val baseUrlKey: Preferences.Key<String> = stringPreferencesKey(BASE_URL_KEY)

    override suspend fun setBaseUrl(baseUrl: String) {
        context.dataStore.edit { settings ->
            settings[baseUrlKey] = baseUrl
        }
    }

    override suspend fun getBaseUrl(): String? {
        return context.dataStore
            .data.first()[baseUrlKey]
    }

    companion object {
        private const val SETTINGS = "settings"
        private const val BASE_URL_KEY = "baseUrlKey"
    }
}
