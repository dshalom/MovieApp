package com.ds.movieapp.data.homeRepo

import com.ds.movieapp.data.models.ConfigurationDto
import com.ds.movieapp.data.models.Genres
import com.ds.movieapp.data.models.MoviesDto
import com.ds.movieapp.di.MovieDBBaseUrl
import com.ds.movieapp.domain.models.Movie
import com.ds.movieapp.domain.repo.HomeRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class HomeRepoImpl @Inject constructor(
    private val client: HttpClient,
    private val storeRepo: StoreRepo,
    @MovieDBBaseUrl private val baseUrl: String
) : HomeRepo {
    override suspend fun getGenres(): Genres {
        return client.get("${baseUrl}genre/movie/list")
            .body<Genres>()
    }

    override suspend fun getMoviesByGenre(genreId: String): List<Movie> {
        return client.get("$baseUrl/discover/movie?with_genres=$genreId")
            .body<MoviesDto>().results.map {
                Movie(
                    adult = it.adult,
                    backdropPath = it.backdropPath ?: "",
                    genreIds = it.genreIds,
                    id = it.id,
                    overview = it.overview,
                    popularity = it.popularity,
                    posterPath = "${storeRepo.getBaseUrl()}w500/${it.posterPath}",
                    releaseDate = it.releaseDate,
                    title = it.title,
                    voteAverage = it.voteAverage
                )
            }
    }

    override suspend fun getConfiguration(): ConfigurationDto {
        return client.get("$baseUrl/configuration")
            .body<ConfigurationDto>()
    }

    override fun onCleared() {
        client.close()
    }
}
