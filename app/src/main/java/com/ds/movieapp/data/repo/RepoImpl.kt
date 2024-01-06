package com.ds.movieapp.data.repo

import com.ds.movieapp.BuildConfig
import com.ds.movieapp.di.MovieDBBaseUrl
import com.ds.movieapp.domain.Repo
import com.ds.movieapp.domain.models.Genres
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class RepoImpl @Inject constructor(
    private val client: HttpClient,
    @MovieDBBaseUrl private val baseUrl: String
) : Repo {
    override suspend fun getGenres(): Genres {
        val genres: Genres =
            client.get("${baseUrl}genre/movie/list?api_key=${BuildConfig.API_KEY}")
                .body()

        println("dsds" + genres)

        return genres
    }

    override fun onCleared() {
        client.close()
    }
}
