package com.ds.movieapp.ui.screens.grid

import com.ds.movieapp.ui.screens.common.viewmodel.UdfViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GridViewModel @Inject constructor() :

    UdfViewModel<GridEvent, GridUiState, GridAction>(
        initialUiState = GridUiState(
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

    override fun handleEvent(event: GridEvent) {
        when (event) {
            GridEvent.OnUpButtonClicked -> sendAction {
                GridAction.NavigateBack
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}
