package com.ds.movieapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogoutDetails(
    @SerialName("session_id")
    val sessionId: String
)
