package com.ds.movieapp.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.ds.movieapp.domain.repo.HomeRepo
import com.ds.movieapp.ui.screens.common.viewmodel.UdfViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepo: HomeRepo) :

    UdfViewModel<HomeEvent, HomeUiState, HomeAction>(
        initialUiState = HomeUiState(
            genres = emptyList(),
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

            val genres = homeRepo.getGenres()
            setUiState {
                copy(
                    genres = genres.genres
                )
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
                    val movies = homeRepo.getMoviesByGenre(event.genreId)

                    movies.results.forEach {
                        Timber.i(it.title)
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        homeRepo.onCleared()
    }
}
