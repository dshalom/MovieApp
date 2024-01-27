package com.ds.movieapp.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.ds.movieapp.data.repo.homeRepo.StoreRepo
import com.ds.movieapp.domain.repo.MoviesRepo
import com.ds.movieapp.domain.repo.WatchListFavoritesRepo
import com.ds.movieapp.ui.screens.common.viewmodel.UdfViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepo: MoviesRepo,
    private val storeRepo: StoreRepo,
    private val watchListFavoritesRepo: WatchListFavoritesRepo
) :

    UdfViewModel<HomeEvent, HomeUiState, HomeAction>(
        initialUiState = HomeUiState(
            genres = emptyList(),
            movies = emptyList(),
            error = false
        )
    ) {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.i("CoroutineExceptionHandler $throwable")
        setUiState {
            copy(
                error = true
            )
        }
    }

    init {
        viewModelScope.launch(exceptionHandler) {

            val config = moviesRepo.getConfiguration()
            storeRepo.setBaseUrl(config.images.baseUrl)

            val genres = moviesRepo.getGenres()
            setUiState {
                copy(
                    genres = genres.genres,
                    selectedGenre = genres.genres.first().id
                )
            }
            val movies = moviesRepo.getMoviesByGenre(genres.genres.first().id.toString())

            setUiState {
                copy(
                    movies = movies.take(5)
                )
            }
        }

        viewModelScope.launch {
            watchListFavoritesRepo.observeFavorites().collect { favourites ->
                setUiState {
                    copy(
                        movies = movies.map { movie ->
                            movie.copy(
                                isFavourite = (
                                    favourites?.count {
                                        it.movieId == movie.id.toString()
                                    } ?: 0
                                    ) > 0
                            )
                        }
                    )
                }
            }
        }
    }

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnUpButtonClicked -> sendAction {
                HomeAction.NavigateBack
            }

            is HomeEvent.OnGenreClicked -> {
                viewModelScope.launch {
                    val movies = moviesRepo.getMoviesByGenre(event.genreId.toString())

                    setUiState {
                        copy(movies = movies.take(5), selectedGenre = event.genreId)
                    }
                }
            }

            is HomeEvent.OnFavouriteClicked -> {
                when (event.isFavourite) {
                    true -> watchListFavoritesRepo.addToFavorites(event.movieId)
                    false -> watchListFavoritesRepo.removeFromFavorites(event.movieId)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        moviesRepo.onCleared()
    }
}
