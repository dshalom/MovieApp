package com.ds.movieapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ds.movieapp.domain.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.request.get
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
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
class HomeViewModel @Inject constructor(private val repo: Repo) : ViewModel() {
    init {

        viewModelScope.launch {

            repo.getGenres()
        }
    }
}
