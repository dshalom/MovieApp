package com.ds.movieapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionId(
    val success: Boolean,
    @SerialName("session_id")
    val sessionId: String
)
