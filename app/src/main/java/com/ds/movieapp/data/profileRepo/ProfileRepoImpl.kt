package com.ds.movieapp.data.profileRepo

import com.ds.movieapp.di.MovieDBBaseUrl
import com.ds.movieapp.domain.repo.ProfileRepo
import io.ktor.client.HttpClient
import timber.log.Timber
import javax.inject.Inject

class ProfileRepoImpl @Inject constructor(
    private val client: HttpClient,
    @MovieDBBaseUrl private val baseUrl: String
) : ProfileRepo {
    override suspend fun authorise() {
        Timber.i("dsds  authorise")
    }

    override fun onCleared() {
        client.close()
    }
}
