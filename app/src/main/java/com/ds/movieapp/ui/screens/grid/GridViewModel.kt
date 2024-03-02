package com.ds.movieapp.ui.screens.grid

import androidx.lifecycle.viewModelScope
import com.ds.movieapp.domain.repo.MoviesRepo
import com.ds.movieapp.ui.screens.common.viewmodel.UdfViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GridViewModel @Inject constructor(
    private val moviesRepo: MoviesRepo
) : UdfViewModel<GridEvent, GridUiState, GridAction>(
    initialUiState = GridUiState(
        movies = emptyList(),
        error = false
    )
) {
    private var job: Job? = null
    private var genreId: String? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.i("CoroutineExceptionHandler $throwable")
        setUiState {
            copy(
                error = true
            )
        }
    }

    override fun handleEvent(event: GridEvent) {
        when (event) {
            GridEvent.OnUpButtonClicked -> sendAction {
                GridAction.NavigateBack
            }

            is GridEvent.OnLoad -> {
                if (uiState.value.movies.isEmpty() || event.genreId != genreId) {
                    job?.cancel()
                    genreId = event.genreId
                    job = viewModelScope.launch(exceptionHandler) {
                        moviesRepo.getMoviesByGenre(
                            genreId = event.genreId,
                            count = MOVIES_TO_SHOW,
                            mostPopular = false
                        )
                            .collect {
                                setUiState {
                                    copy(movies = it)
                                }
                            }
                    }
                }
            }

            is GridEvent.OnFavouriteClicked -> {
                when (event.isFavourite) {
                    true -> moviesRepo.addToFavorites(event.movieId)
                    false -> moviesRepo.removeFromFavorites(event.movieId)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    companion object {
        private const val MOVIES_TO_SHOW = 20
    }
}
