package com.ds.movieapp.data.homeRepo

import com.ds.movieapp.BuildConfig
import com.ds.movieapp.di.MovieDBBaseUrl
import com.ds.movieapp.domain.models.Genres
import com.ds.movieapp.domain.repo.HomeRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class HomeRepoImpl @Inject constructor(
    private val client: HttpClient,
    @MovieDBBaseUrl private val baseUrl: String
) : HomeRepo {
    override suspend fun getGenres(): Genres {
        return client.get("${baseUrl}genre/movie/list?api_key=${BuildConfig.API_KEY}")
            .body<Genres>()
    }

    override fun onCleared() {
        client.close()
    }
}
