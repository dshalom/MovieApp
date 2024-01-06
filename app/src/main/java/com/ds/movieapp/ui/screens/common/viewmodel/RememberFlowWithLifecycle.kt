package com.ds.movieapp.ui.screens.common.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
@SuppressLint("StateFlowValueCalledInComposition")
fun <T> StateFlow<T>.rememberCollectWithLifecycle(
    minActiveState: Lifecycle.State = STARTED
): State<T> = rememberFlowWithLifecycle(minActiveState).collectAsState(initial = value)

@Composable
fun <T> StateFlow<T>.rememberFlowWithLifecycle(
    minActiveState: Lifecycle.State = STARTED
): Flow<T> = LocalLifecycleOwner.current.let { lifecycleOwner ->
    remember(this, lifecycleOwner) {
        flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState)
    }
}
