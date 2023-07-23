package com.example.minimlistcompose.ui.presentation


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

import com.example.minimlistcompose.response.AllAnimeResponse
import com.example.minimlistcompose.viewmodel.AnimeViewModel


@Composable
fun FavoriteScreen(navController: NavController, viewModel: AnimeViewModel) {
    var animeList by remember { mutableStateOf(AllAnimeResponse((emptyList()), "")) }

    LaunchedEffect(Unit) {
        viewModel.getFavorite()
        viewModel.animeListFavorite.observeForever {
            animeList = it
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {

            AnimeList(animeList = animeList, navController = navController)

        }
    }
}

