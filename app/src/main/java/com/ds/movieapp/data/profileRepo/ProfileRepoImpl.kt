package com.ds.movieapp.data.profileRepo

import com.ds.movieapp.BuildConfig
import com.ds.movieapp.data.models.RequestToken
import com.ds.movieapp.data.models.SessionId
import com.ds.movieapp.di.MovieDBBaseUrl
import com.ds.movieapp.domain.repo.ProfileRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType
import timber.log.Timber
import javax.inject.Inject

class ProfileRepoImpl @Inject constructor(
    private val client: HttpClient,
    @MovieDBBaseUrl private val baseUrl: String
) : ProfileRepo {
    override suspend fun authorise() {
        val rt = client.get("$baseUrl/authentication/token/new")
            .body<RequestToken>()

        //  val st = client.
        Timber.i("dsds rt: $rt")

        val authorisedRequestToken =
            client.post("$baseUrl/authentication/token/validate_with_login?request_token=${rt.requestToken}") {
                contentType(ContentType.Application.Json)
                setBody("""{"username": "${BuildConfig.USERNAME}", "password" : "${BuildConfig.PASSWORD}" }""")
            }.body<RequestToken>()

        val sessionId = client.post("$baseUrl/authentication/session/new?request_token=${authorisedRequestToken.requestToken}")
            .body<SessionId>()

        Timber.i("dsds sessionId: $sessionId")
    }

    override fun onCleared() {
        client.close()
    }
}
