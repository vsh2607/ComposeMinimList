package com.example.minimlistcompose.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MenuItem(val icon: ImageVector) {
    abstract val title: String
    abstract val route: String

    object Home : MenuItem(Icons.Default.Home) {
        override val title: String = "Home"
        override val route: String = Screen.Home.route

    }

    object Favorite : MenuItem(Icons.Default.Favorite) {
        override val title: String = "Favorite"
        override val route: String = Screen.Favorite.route
    }

    object Profile : MenuItem(Icons.Default.AccountCircle) {
        override val title: String = "Profile"
        override val route: String = Screen.Profile.route
    }
}
