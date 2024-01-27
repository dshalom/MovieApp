package com.ds.movieapp.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.ds.movieapp.data.repo.homeRepo.StoreRepo
import com.ds.movieapp.domain.repo.MoviesRepo
import com.ds.movieapp.ui.screens.common.viewmodel.UdfViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepo: MoviesRepo,
    private val storeRepo: StoreRepo
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

            moviesRepo.getMoviesByGenre(genres.genres.first().id)
                .take(MOVIES_TO_SHOW)
                .collect {
                    setUiState {
                        copy(movies = it)
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
                setUiState {
                    copy(selectedGenre = event.genreId)
                }
                job?.cancel()
                job = viewModelScope.launch {
                    moviesRepo.getMoviesByGenre(event.genreId)
                        .take(MOVIES_TO_SHOW)
                        .collect {
                            setUiState {
                                copy(movies = it)
                            }
                        }
                }
            }

            is HomeEvent.OnFavouriteClicked -> {
                when (event.isFavourite) {
                    true -> moviesRepo.addToFavorites(event.movieId)
                    false -> moviesRepo.removeFromFavorites(event.movieId)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        moviesRepo.onCleared()
    }

    companion object {
        private const val MOVIES_TO_SHOW = 5
    }
}
