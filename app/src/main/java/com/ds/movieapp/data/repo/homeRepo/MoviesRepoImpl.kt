package com.ds.movieapp.data.repo.homeRepo

import com.ds.movieapp.data.models.ConfigurationDto
import com.ds.movieapp.data.models.Genres
import com.ds.movieapp.data.models.MovieDetailsDto
import com.ds.movieapp.data.models.MoviesDto
import com.ds.movieapp.di.MovieDBBaseUrl
import com.ds.movieapp.domain.models.Movie
import com.ds.movieapp.domain.models.MovieDetails
import com.ds.movieapp.domain.repo.MoviesRepo
import com.ds.movieapp.domain.repo.WatchListFavoritesRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MoviesRepoImpl @Inject constructor(
    private val client: HttpClient,
    private val storeRepo: StoreRepo,
    private val watchListFavoritesRepo: WatchListFavoritesRepo,
    @MovieDBBaseUrl private val baseUrl: String
) : MoviesRepo {
    override suspend fun getGenres(): Genres {
        return client.get("$baseUrl/genre/movie/list")
            .body<Genres>()
    }

    override suspend fun getMoviesByGenre(genreId: String): Flow<List<Movie>> {
        return flowOf(
            client.get("$baseUrl/discover/movie?with_genres=$genreId")
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
                        voteAverage = it.voteAverage,
                        isFavourite = false
                    )
                }
        ).combine(watchListFavoritesRepo.observeFavorites()) { mv, fv ->

            mv.map { m ->
                m.copy(
                    isFavourite = (
                        fv?.count { f ->
                            f.movieId == m.id
                        } ?: 0
                        ) > 0
                )
            }
        }
    }

    override suspend fun getMovieById(id: String): Flow<MovieDetails> {
        val dto = client.get("$baseUrl/movie/$id")
            .body<MovieDetailsDto>()

        return flowOf(
            MovieDetails(
                id = dto.id,
                title = dto.title,
                backdropPath = "${storeRepo.getBaseUrl()}w1280/${dto.backdropPath}"
            )
        ).combine(watchListFavoritesRepo.observeFavorites()) { mv, fv ->

            mv.copy(
                isFavourite = (
                    fv?.count { f ->
                        f.movieId == mv.id
                    } ?: 0
                    ) > 0
            )
        }
    }

    override suspend fun getConfiguration(): ConfigurationDto {
        return client.get("$baseUrl/configuration")
            .body<ConfigurationDto>()
    }

    override fun addToFavorites(movieId: String) {
        watchListFavoritesRepo.addToFavorites(movieId)
    }

    override fun removeFromFavorites(movieId: String) {
        watchListFavoritesRepo.removeFromFavorites(movieId)
    }

    override fun onCleared() {
        client.close()
    }
}
