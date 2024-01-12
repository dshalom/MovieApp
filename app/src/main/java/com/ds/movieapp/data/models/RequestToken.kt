package com.ds.movieapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestToken(
    @SerialName("expires_at")
    val expiresAt: String,
    @SerialName("request_token")
    val requestToken: String,
    val success: Boolean
)
