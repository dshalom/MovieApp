package com.ds.movieapp.data.profileRepo

import com.ds.movieapp.BuildConfig
import com.ds.movieapp.data.models.DSR
import com.ds.movieapp.data.models.RequestToken
import com.ds.movieapp.di.MovieDBBaseUrl
import com.ds.movieapp.domain.repo.ProfileRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class ProfileRepoImpl @Inject constructor(
    private val client: HttpClient,
    @MovieDBBaseUrl private val baseUrl: String
) : ProfileRepo {
    override suspend fun authorise() {
        val requestToken = client.get("$baseUrl/authentication/token/new")
            .body<RequestToken>()

        val authorisedRequestToken =
            client.post("$baseUrl/authentication/token/validate_with_login?request_token=${requestToken.requestToken}") {
                contentType(ContentType.Application.Json)
                setBody(
                    DSR(
                        username = BuildConfig.USERNAME,
                        password = BuildConfig.PASSWORD,
                        requestToken = requestToken.requestToken
                    )
                )
            }.body<RequestToken>()

//        val sessionId = client.post("$baseUrl/authentication/session/new?request_token=${authorisedRequestToken.requestToken}")
//            .body<SessionId>()
//
//        Timber.i("dsds sessionId: $sessionId")
    }

    override fun onCleared() {
        client.close()
    }
}
