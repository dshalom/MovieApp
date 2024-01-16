package com.ds.movieapp.ui.screens.details

import com.ds.movieapp.domain.repo.MoviesRepo
import com.ds.movieapp.ui.screens.common.viewmodel.UdfViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val moviesRepo: MoviesRepo
) : UdfViewModel<DetailsEvent, DetailsUiState, DetailsAction>(
    initialUiState = DetailsUiState(
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

    override fun handleEvent(event: DetailsEvent) {
    }

    override fun onCleared() {
        super.onCleared()
    }
}
