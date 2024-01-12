package com.ds.movieapp.ui.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ProfileContent(
    profileUiState: ProfileUiState,
    event: (ProfileEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Authorised : ${profileUiState.authorised}")

            Button(onClick = {
                event(ProfileEvent.OnAuthoriseClick)
            }) {
                Text(text = "Authorise")
            }
        }
    }
}
