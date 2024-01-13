package com.ds.movieapp.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ds.movieapp.ui.screens.Screen
import com.ds.movieapp.ui.screens.common.viewmodel.rememberCollectWithLifecycle
import com.ds.movieapp.ui.screens.home.HomeContent
import com.ds.movieapp.ui.screens.home.HomeViewModel
import com.ds.movieapp.ui.screens.items
import com.ds.movieapp.ui.screens.profile.ProfileContent
import com.ds.movieapp.ui.screens.profile.ProfileViewModel
import com.ds.movieapp.ui.screens.search.SearchContent

@Composable
fun MainContent(
    homeViewModel: HomeViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { DoingNavigationBar(navController) }

    ) { paddingValues ->

        NavHost(
            navController,
            startDestination = Screen.TasksScreen.route,
            Modifier.padding(paddingValues)
        ) {
            composable(Screen.TasksScreen.route) {
                val homeUiState = homeViewModel.uiState.rememberCollectWithLifecycle()
                HomeContent(
                    homeUiState = homeUiState.value,
                    event = homeViewModel::handleEvent
                )
            }
            composable(Screen.ProfileScreen.route) {
                val profileUiState = profileViewModel.uiState.rememberCollectWithLifecycle()
                ProfileContent(
                    profileUiState = profileUiState.value,
                    event = profileViewModel::handleEvent
                )
            }
            composable(Screen.SearchScreen.route) { SearchContent() }
        }
    }
}

@Composable
fun DoingNavigationBar(navController: NavHostController) {
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar {
        items.forEachIndexed { index, screen ->
            val selected = selectedItem == index
            NavigationBarItem(
                selected = selected,
                onClick = {
                    selectedItem = index
                    /* navigate to selected route */
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (selected) screen.selectedIcon else screen.unSelectedIcon,
                        contentDescription = screen.title
                    )
                },
                label = { Text(text = screen.title) }
            )
        }
    }
}
