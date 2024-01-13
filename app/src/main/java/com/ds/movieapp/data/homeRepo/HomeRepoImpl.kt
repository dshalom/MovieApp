package com.ds.movieapp.data.homeRepo

import com.ds.movieapp.data.models.Genres
import com.ds.movieapp.data.models.MoviesDto
import com.ds.movieapp.di.MovieDBBaseUrl
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
        return client.get("${baseUrl}genre/movie/list")
            .body<Genres>()
    }

    override suspend fun getMoviesByGenre(genreId: String): MoviesDto {
        return client.get("$baseUrl/discover/movie?with_genres=$genreId")
            .body<MoviesDto>()
    }

    override fun onCleared() {
        client.close()
    }
}
