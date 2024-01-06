package com.ds.movieapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MovieDBBaseUrl

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val TIME_OUT = 60_000

    @Provides
    @Singleton
    fun providesKtor(): HttpClient {
        val client = HttpClient(Android) {
            expectSuccess = true
            engine {
                // this: AndroidEngineConfig
                connectTimeout = TIME_OUT
                socketTimeout = TIME_OUT
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.d("Logger Ktor =>", message)
                    }
                }
                level = LogLevel.ALL
            }

            install(ResponseObserver) {
                onResponse { response ->
                    Timber.d("HTTP status:", "${response.status.value}")
                }
            }
        }
        return client
    }

    @Provides
    @MovieDBBaseUrl
    @Singleton
    fun provideBaseUrl(): String = "https://api.themoviedb.org/3/"
}
