package com.example.minimlistcompose.ui.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.minimlistcompose.data.MenuItem
import com.example.minimlistcompose.data.Screen
import com.example.minimlistcompose.viewmodel.AnimeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyNavDrawerApp() {

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val viewModel = AnimeViewModel()
    val items = listOf(
        MenuItem.Home,
        MenuItem.Favorite,
        MenuItem.Profile
    )
    val selectedItem = remember { mutableStateOf(items[0]) }

    Scaffold(
        topBar = {
            MyTopBar(
                onMenuClick = {
                    scope.launch {
                        if (drawerState.isClosed) {
                            drawerState.open()
                        } else {
                            drawerState.close()
                        }
                    }
                }
            )
        },
    ) { paddingValues ->
        ModalNavigationDrawer(
            modifier = Modifier.padding(paddingValues),
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(Modifier.height(12.dp))
                    items.forEach {
                        NavigationDrawerItem(
                            icon = { Icon(it.icon, contentDescription = null) },
                            label = { Text(it.title) },
                            selected = it == selectedItem.value,
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                    navController.navigate(it.route)
                                }
                                selectedItem.value = it
                            },
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }

                }
            },
            content = {
                NavHost(navController, startDestination = Screen.Home.route) {
                    composable(Screen.Home.route) {
                        HomeScreen(navController, viewModel)
                    }
                    composable(Screen.Favorite.route) {
                        FavoriteScreen(navController, viewModel)
                    }
                    composable(Screen.Profile.route) {
                        ProfileScreen()
                    }
                    composable(Screen.AnimeDetail.route + "/{animeId}") { backStackEntry ->
                        val arguments = requireNotNull(backStackEntry.arguments)
                        val animeId = requireNotNull(arguments.getString("animeId"))
                        AnimeDetailScreen(animeId, viewModel)
                    }
                }
            }
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(onMenuClick: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                onMenuClick()
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Icon"
                )
            }
        },
        title = {
            Text(text = "MinimList Compose")
        },
    )
}
