package com.ds.movieapp.data.profileRepo

import com.ds.movieapp.BuildConfig
import com.ds.movieapp.data.homeRepo.SessionRepo
import com.ds.movieapp.data.models.LoginDetails
import com.ds.movieapp.data.models.LogoutDetails
import com.ds.movieapp.data.models.RequestToken
import com.ds.movieapp.data.models.SessionId
import com.ds.movieapp.di.MovieDBBaseUrl
import com.ds.movieapp.domain.repo.AuthenticationRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthenticationRepoImpl @Inject constructor(
    private val client: HttpClient,
    private val sessionRepo: SessionRepo,
    @MovieDBBaseUrl
    private val baseUrl: String
) : AuthenticationRepo {
    override suspend fun login() {
        val requestToken = client.get("$baseUrl/authentication/token/new")
            .body<RequestToken>()

        val authorisedRequestToken =
            client.post("$baseUrl/authentication/token/validate_with_login") {
                contentType(ContentType.Application.Json)
                setBody(
                    LoginDetails(
                        username = BuildConfig.USERNAME,
                        password = BuildConfig.PASSWORD,
                        requestToken = requestToken.requestToken
                    )
                )
            }.body<RequestToken>()

        val sessionId =
            client.post("$baseUrl/authentication/session/new?request_token=${authorisedRequestToken.requestToken}")
                .body<SessionId>()

        sessionRepo.storeSessionId(sessionId.sessionId)
    }

    override suspend fun logout() {
        sessionRepo.getSessionId()?.let { sessionId ->
            client.delete("$baseUrl/authentication/session") {
                contentType(ContentType.Application.Json)
                setBody(
                    LogoutDetails(sessionId = sessionId)
                )
            }
        }
        sessionRepo.logout()
    }

    override fun onCleared() {
        client.close()
    }

    override suspend fun loggedIn(): Flow<Boolean> {
        return sessionRepo.observeLoggedIn()
    }
}
