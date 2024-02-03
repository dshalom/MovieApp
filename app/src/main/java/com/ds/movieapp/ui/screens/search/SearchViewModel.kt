package com.ds.movieapp.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ds.movieapp.domain.models.SearchResult
import com.ds.movieapp.domain.repo.MoviesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val moviesRepo: MoviesRepo
) : ViewModel() {

    private val _movies: MutableStateFlow<List<SearchResult>> = MutableStateFlow(emptyList())
    val movies = _movies.asStateFlow()

    fun onSearchTextChanged(searchText: String) {
        viewModelScope.launch {
            _movies.update {
                moviesRepo.searchMovies(searchText)
            }
        }
    }
}
