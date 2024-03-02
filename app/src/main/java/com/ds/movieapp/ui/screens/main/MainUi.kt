package com.ds.movieapp.ui.screens.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ds.movieapp.ui.screens.Screen
import com.ds.movieapp.ui.screens.common.viewmodel.rememberCollectWithLifecycle
import com.ds.movieapp.ui.screens.details.DetailsUi
import com.ds.movieapp.ui.screens.details.DetailsViewModel
import com.ds.movieapp.ui.screens.grid.GridUi
import com.ds.movieapp.ui.screens.grid.GridViewModel
import com.ds.movieapp.ui.screens.home.HomeUi
import com.ds.movieapp.ui.screens.home.HomeViewModel
import com.ds.movieapp.ui.screens.items
import com.ds.movieapp.ui.screens.profile.ProfileUi
import com.ds.movieapp.ui.screens.profile.ProfileViewModel
import com.ds.movieapp.ui.screens.search.SearchUi

@Composable
fun MainUi(
    homeViewModel: HomeViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    gridViewModel: GridViewModel = hiltViewModel(),
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute by remember {
        derivedStateOf {
            currentBackStackEntry?.destination?.route ?: Screen.HomeScreen.route
        }
    }

    Scaffold(
        topBar = { MoviesTopBar(currentRoute, navController) },
        bottomBar = { MoviesNavigationBar(navController) }

    ) { paddingValues ->

        NavHost(
            navController,
            startDestination = Screen.HomeScreen.route,
            Modifier.padding(paddingValues)
        ) {
            composable(Screen.HomeScreen.route) {
                val homeUiState = homeViewModel.uiState.rememberCollectWithLifecycle()
                HomeUi(
                    homeUiState = homeUiState.value,
                    navController,
                    event = homeViewModel::handleEvent
                )
            }
            composable(Screen.ProfileScreen.route) {
                val profileUiState = profileViewModel.uiState.rememberCollectWithLifecycle()
                ProfileUi(
                    profileUiState = profileUiState.value,
                    event = profileViewModel::handleEvent
                )
            }
            composable(
                "${Screen.GridScreen.route}/{genreId}",
                arguments = listOf(
                    navArgument("genreId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val gridUiState = gridViewModel.uiState.rememberCollectWithLifecycle()
                GridUi(
                    backStackEntry.arguments?.getString("genreId") ?: "",
                    gridUiState.value,
                    navController,
                    event = gridViewModel::handleEvent
                )
            }
            composable(
                "${Screen.DetailsScreen.route}/{movieId}",
                arguments = listOf(
                    navArgument("movieId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val detailsUiState = detailsViewModel.uiState.rememberCollectWithLifecycle()
                DetailsUi(
                    backStackEntry.arguments?.getString("movieId") ?: "",
                    detailsUiState.value,
                    event = detailsViewModel::handleEvent
                )
            }
            composable(Screen.SearchScreen.route) { SearchUi(navController) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesTopBar(currentRoute: String, navController: NavHostController) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Movie Mania",
                    style = MaterialTheme.typography.titleLarge
                )

                Icon(
                    modifier = Modifier
                        .padding(end = 8.dp),
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "profile picture"
                )
            }
        },

        navigationIcon = {
            if (currentRoute.startsWith(Screen.DetailsScreen.route) || currentRoute.startsWith(
                    Screen.GridScreen.route
                )
            ) {
                IconButton({ navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "menu items"
                    )
                }
            }
        }
    )
}

@Composable
fun MoviesNavigationBar(navController: NavHostController) {
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
                    (if (selected) screen.selectedIcon else screen.unSelectedIcon)?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = screen.title
                        )
                    }
                },
                label = { Text(text = screen.title) }
            )
        }
    }
}
