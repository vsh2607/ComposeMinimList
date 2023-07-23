package com.example.minimlistcompose.ui.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.minimlistcompose.response.AllAnimeResponse
import com.example.minimlistcompose.viewmodel.AnimeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel : AnimeViewModel) {
    var animeList by remember { mutableStateOf(AllAnimeResponse((emptyList()),"")) }
    var searchText by remember { mutableStateOf("") }


    val onSearchClick: (String) -> Unit = {

            viewModel.searchAnime(it)
            viewModel.animeList.observeForever { result ->
                animeList = result
            }

    }
    LaunchedEffect(Unit) {
        onSearchClick("")
    }


    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .onKeyEvent { event ->
                        if (event.type == KeyEventType.KeyUp) {
                            Log.d("TAG", "search Text : $searchText")
                            onSearchClick(searchText)
                        }
                        false
                    },
                placeholder = { Text(text = "Search") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(

                    onSearch = {
                        onSearchClick(searchText)
                        Log.d("TAG", searchText)}
                )
            )


            AnimeList(animeList = animeList, navController = navController)

        }
    }


}