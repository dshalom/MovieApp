package com.ds.movieapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginDetails(
    val username: String,
    val password: String,
    @SerialName("request_token")
    val requestToken: String
)
