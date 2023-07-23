package com.example.minimlistcompose.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.minimlistcompose.data.Screen
import com.example.minimlistcompose.response.AllAnimeResponse

@Composable
fun AnimeList(animeList: AllAnimeResponse, navController: NavController) {
    if (animeList.message == "1") {
        LazyColumn {
            items(animeList.data) { item ->
                AnimeItem(
                    animeThumb = item.image_thumb,
                    animeName = item.name,
                    animeGenre = item.genre,
                    animeId = item.id,
                    navController
                )
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No results found",
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeItem(
    animeThumb: String,
    animeName: String,
    animeGenre: String,
    animeId: String,
    navController: NavController
) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        onClick = { navController.navigate(Screen.AnimeDetail.route + "/$animeId") }

    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            AsyncImage(
                model = animeThumb,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(70.dp, 100.dp)
                    .clip(RectangleShape)
                    .background(Color.White)
            )

            Column(modifier = Modifier.padding(6.dp)) {

                Text(
                    text = animeName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(text = animeGenre, fontSize = 14.sp, fontWeight = FontWeight.Normal)
            }
        }

    }

    Divider()

}


