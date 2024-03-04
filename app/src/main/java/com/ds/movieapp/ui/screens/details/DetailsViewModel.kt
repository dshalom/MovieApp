package com.ds.movieapp.ui.screens.details

import androidx.lifecycle.viewModelScope
import com.ds.movieapp.domain.repo.MoviesRepo
import com.ds.movieapp.ui.screens.common.viewmodel.UdfViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val moviesRepo: MoviesRepo
) : UdfViewModel<DetailsEvent, DetailsUiState, DetailsAction>(
    initialUiState = DetailsUiState(
        movieDetails = null,
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
        when (event) {
            is DetailsEvent.OnLoad -> {
                viewModelScope.launch(exceptionHandler) {
                    moviesRepo.getMovieDetailsById(event.movieId)
                        .collect {
                            setUiState {
                                copy(movieDetails = it)
                            }
                        }
                }
            }

            DetailsEvent.OnUpButtonClicked -> {}
            is DetailsEvent.OnFavouriteClicked -> {
                when (event.isFavourite) {
                    true -> moviesRepo.addToFavorites(event.movieId)
                    false -> moviesRepo.removeFromFavorites(event.movieId)
                }
            }
        }
    }
}
