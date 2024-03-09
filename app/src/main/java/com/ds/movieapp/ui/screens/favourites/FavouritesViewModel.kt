package com.ds.movieapp.ui.screens.favourites

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
class FavouritesViewModel @Inject constructor(
    private val moviesRepo: MoviesRepo
) : UdfViewModel<FavouritesEvent, FavouritesUiState, FavouritesAction>(
    initialUiState = FavouritesUiState(
        loggedIn = false,
        requestToken = "",
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
            moviesRepo.getFavoriteMovies()
                .collect {
                    Timber.i("dsds $it")
                }
        }
    }

    override fun handleEvent(event: FavouritesEvent) {
    }
}
