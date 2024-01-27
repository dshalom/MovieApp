package com.ds.movieapp.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.ds.movieapp.data.repo.homeRepo.StoreRepo
import com.ds.movieapp.domain.repo.MoviesRepo
import com.ds.movieapp.domain.repo.WatchListFavoritesRepo
import com.ds.movieapp.ui.screens.common.viewmodel.UdfViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
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

    private var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.i("CoroutineExceptionHandler $throwable")
        setUiState {
            copy(
                error = true
            )
        }
    }

    init {
        job = viewModelScope.launch(exceptionHandler) {

            val config = moviesRepo.getConfiguration()
            storeRepo.setBaseUrl(config.images.baseUrl)

            val genres = moviesRepo.getGenres()
            setUiState {
                copy(
                    genres = genres.genres,
                    selectedGenre = genres.genres.first().id
                )
            }

            setMoviesByGenre(genres.genres.first().id)
        }
    }

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnUpButtonClicked -> sendAction {
                HomeAction.NavigateBack
            }

            is HomeEvent.OnGenreClicked -> {
                job?.cancel()
                job = viewModelScope.launch {
                    setMoviesByGenre(event.genreId)
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

    private suspend fun setMoviesByGenre(genre: String) {
        val movies = moviesRepo.getMoviesByGenre(genre)

        setUiState {
            copy(selectedGenre = genre)
        }
        flowOf(movies).combine(
            watchListFavoritesRepo.observeFavorites()
        ) { mv, fv ->
            setUiState {
                copy(
                    movies = mv.map { m ->
                        m.copy(
                            isFavourite = (
                                fv?.count { f ->
                                    f.movieId == m.id
                                } ?: 0
                                ) > 0
                        )
                    }.take(MOVIES_TO_SHOW)
                )
            }
        }.collect()
    }

    override fun onCleared() {
        super.onCleared()
        moviesRepo.onCleared()
    }

    companion object {
        private const val MOVIES_TO_SHOW = 5
    }
}
