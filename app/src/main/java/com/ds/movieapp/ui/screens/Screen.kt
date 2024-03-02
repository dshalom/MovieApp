package com.ds.movieapp.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector? = null,
    val unSelectedIcon: ImageVector? = null
) {
    data object HomeScreen : Screen(
        route = "home",
        title = "Home",
        selectedIcon = Icons.Filled.List,
        unSelectedIcon = Icons.Outlined.List
    )

    data object SearchScreen : Screen(
        route = "search",
        title = "Search",
        selectedIcon = Icons.Filled.Search,
        unSelectedIcon = Icons.Outlined.Search
    )

    data object GridScreen : Screen(
        route = "grid",
        title = "Grid"
    )

    data object DetailsScreen : Screen(
        route = "details",
        title = "Details"
    )

    data object ProfileScreen : Screen(
        route = "profile",
        title = "Profile",
        selectedIcon = Icons.Filled.Person,
        unSelectedIcon = Icons.Outlined.Person
    )

    data object FavouritesScreen : Screen(
        route = "favourites",
        title = "Favourites",
        selectedIcon = Icons.Filled.Favorite,
        unSelectedIcon = Icons.Outlined.Favorite
    )
}

val items = listOf(
    Screen.HomeScreen,
    Screen.SearchScreen,
    Screen.FavouritesScreen
)
