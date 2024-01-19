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
                    job = viewModelScope.launch {
                        val movies = moviesRepo.getMoviesByGenre(event.genreId)
                        setUiState {
                            copy(movies = movies)
                        }
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}
