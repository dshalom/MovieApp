package com.ds.movieapp.data.repo.homeRepo

import com.ds.movieapp.data.models.CastDto
import com.ds.movieapp.data.models.ConfigurationDto
import com.ds.movieapp.data.models.Genres
import com.ds.movieapp.data.models.MovieDetailsDto
import com.ds.movieapp.data.models.MoviesDto
import com.ds.movieapp.di.MovieDBBaseUrl
import com.ds.movieapp.domain.models.CastItem
import com.ds.movieapp.domain.models.Movie
import com.ds.movieapp.domain.models.MovieDetails
import com.ds.movieapp.domain.models.SearchResult
import com.ds.movieapp.domain.repo.MoviesRepo
import com.ds.movieapp.domain.repo.WatchListFavoritesRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val MAX_CAST = 4

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

    override suspend fun getMoviesByGenre(
        genreId: String,
        count: Int,
        mostPopular: Boolean
    ): Flow<List<Movie>> {
        val queryString = when (mostPopular) {
            true -> "$baseUrl/discover/movie?with_genres=$genreId&sort_by=vote_count.desc"
            false -> "$baseUrl/discover/movie?with_genres=$genreId"
        }
        return flowOf(

            client.get(queryString)
                .body<MoviesDto>().results
                .take(count)
                .map {
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
        ).combine(watchListFavoritesRepo.observeFavorites()) { movie, favourite ->
            movie.map { mv ->
                mv.copy(
                    isFavourite = (
                        favourite?.count { fv ->
                            fv.movieId == mv.id
                        } ?: 0
                        ) > 0
                )
            }
        }
    }

    override suspend fun getFavoriteMovies(): Flow<List<MovieDetails>> {
        return watchListFavoritesRepo.observeFavorites().map {
            it?.map { favouriteId ->
                getMovieDetails(favouriteId.movieId)
            } ?: emptyList()
        }
    }

    override suspend fun searchMovies(query: String): List<SearchResult> {
        return client.get("$baseUrl/search/movie?query=$query")
            .body<MoviesDto>().results.map {
                SearchResult(
                    backdropPath = "${storeRepo.getBaseUrl()}w780/${it.backdropPath}",
                    id = it.id,
                    title = it.title
                )
            }
    }

    override suspend fun getMovieDetailsById(id: String): Flow<MovieDetails> {
        return flowOf(
            getMovieDetails(id)
        ).combine(watchListFavoritesRepo.observeFavorites()) { movie, favourite ->
            movie.copy(
                isFavourite = (
                    favourite?.count { fav ->
                        fav.movieId == movie.id
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

    private suspend fun getMovieDetails(id: String): MovieDetails {
        val dto = client.get("$baseUrl/movie/$id")
            .body<MovieDetailsDto>()

        val castDto = client.get("$baseUrl/movie/$id/credits")
            .body<CastDto>()

        return MovieDetails(
            id = dto.id,
            title = dto.title,
            director = castDto.crew.firstOrNull {
                it.job == "Director"
            }?.name,
            backdropPath = "${storeRepo.getBaseUrl()}w1280/${dto.backdropPath}",
            tagline = dto.tagline,
            overview = dto.overview,
            genres = dto.genres.map { it.name },
            voteAverage = dto.voteAverage.toFloat(),
            cast = castDto.cast.take(MAX_CAST).map {
                CastItem(
                    castId = it.castId,
                    character = it.character,
                    creditId = it.creditId,
                    id = it.id,
                    name = it.name,
                    order = it.order,
                    profilePath = "${storeRepo.getBaseUrl()}w185/${it.profilePath}"
                )
            }
        )
    }
}
