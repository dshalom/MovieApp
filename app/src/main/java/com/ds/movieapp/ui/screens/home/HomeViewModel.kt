package com.ds.movieapp.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.ds.movieapp.domain.Repo
import com.ds.movieapp.ui.screens.common.viewmodel.UdfViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import timber.log.Timber
import javax.inject.Inject

@Serializable
data class Resp(
    val quotes: List<Quote>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

@Serializable
data class Quote(
    val id: Int,
    val quote: String,
    val author: String
)

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: Repo) :

    UdfViewModel<HomeEvent, HomeUiState, HomeAction>(
        initialUiState = HomeUiState(
            genres = emptyList(),
            e = false
        )
    ) {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->

        Timber.i("CoroutineExceptionHandler $throwable")
        setUiState {
            copy(
                e = true
            )
        }
    }

    init {

        viewModelScope.launch(exceptionHandler) {

            val g = repo.getGenres()
            setUiState {
                copy(
                    genres = g.genres.map { it.name }
                )
            }
        }
    }

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnUpButtonClick -> sendAction {
                HomeAction.NavigateBack
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        repo.onCleared()
    }
}
