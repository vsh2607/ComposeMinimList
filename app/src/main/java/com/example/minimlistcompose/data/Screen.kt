package com.example.minimlistcompose.data

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object FavoriteDetail : Screen("favoriteDetail")
    object AnimeDetail : Screen("animeDetail")
}