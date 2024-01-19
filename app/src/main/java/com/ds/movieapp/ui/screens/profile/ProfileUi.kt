package com.ds.movieapp.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier

@Composable
fun ProfileUi(
    profileUiState: ProfileUiState,
    event: (ProfileEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Logged in : ${profileUiState.loggedIn}",
                style = MaterialTheme.typography.headlineLarge
            )

            Button(
                onClick = {
                    event(ProfileEvent.OnLoginClick)
                },
                enabled = !profileUiState.loggedIn
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            Button(
                onClick = {
                    event(ProfileEvent.OnLogOutClick)
                },
                enabled = profileUiState.loggedIn
            ) {
                Text(
                    text = "Logout",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    }
}
