package com.example.minimlistcompose.ui.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import coil.compose.AsyncImage
import com.example.minimlistcompose.api.ApiConfig
import com.example.minimlistcompose.response.AnimeByIdResponse
import com.example.minimlistcompose.response.Data
import com.example.minimlistcompose.response.FavoriteResponse
import com.example.minimlistcompose.viewmodel.AnimeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun AnimeDetailScreen(id: String, viewModel: AnimeViewModel) {
    var animeDetail by remember { mutableStateOf(Data("", "", "", "", "", "", "", "")) }

    LaunchedEffect(id) {
        viewModel.getAnimeById(id)
        viewModel.animeDetail.observeForever {
            animeDetail = it.data
        }
    }

    val onFavoriteClick: (String) -> Unit = {
        if (animeDetail.is_favorited == "0") {
            ApiConfig.getApiService().addFavorite(it).enqueue(object : Callback<FavoriteResponse> {
                override fun onResponse(call: Call<FavoriteResponse>, response: Response<FavoriteResponse>) {
                    if (response.isSuccessful) {


                        animeDetail = animeDetail.copy(is_favorited = "1")
                        Log.d("TAG", response.body().toString())
                    } else {
                        Log.d("TAG", response.message())
                    }
                }

                override fun onFailure(call: Call<FavoriteResponse>, t: Throwable) {
                    Log.d("TAG", t.message.toString())
                }
            })
        } else {
            ApiConfig.getApiService().deleteFavorite(it).enqueue(object :
                Callback<FavoriteResponse> {
                override fun onResponse(call: Call<FavoriteResponse>, response: Response<FavoriteResponse>) {
                    if (response.isSuccessful) {
                        val favoriteResponse = response.body()
                        animeDetail = animeDetail.copy(is_favorited = "0")
                        Log.d("TAG", response.body().toString())
                    } else {
                        Log.d("TAG", response.message())
                    }
                }

                override fun onFailure(call: Call<FavoriteResponse>, t: Throwable) {
                    Log.d("TAG", t.message.toString())
                }
            })
        }
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Row(modifier = Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp)) {
                AsyncImage(
                    model = animeDetail.image_thumb,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .width(100.dp)
                        .clip(RectangleShape)
                )

                Column {
                    Text(
                        text = animeDetail.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )

                    Text(
                        text = animeDetail.genre,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )

                    Row{
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Favorite",
                            tint = Color.Yellow,
                            modifier = Modifier.padding(start = 16.dp, top = 14.dp)
                        )
                        Text(
                            text = animeDetail.score,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 10.dp, end = 16.dp, top = 16.dp)
                        )
                    }

                }

            }

        }
        item {
            Text(text = "Synopsis :", fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 16.dp, top = 32.dp))
        }

        item {
            Text(
                text = animeDetail.synopsis,
                fontSize = 14.sp,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(top = 12.dp ,start = 16.dp, end = 16.dp)
            )
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                FloatingActionButton(
                    onClick = {

                        onFavoriteClick(animeDetail.id)

                    },
                    modifier = Modifier
                        .size(56.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        tint = if (animeDetail.is_favorited == "1") Color(android.graphics.Color.parseColor("#FCAEAE")) else Color.White
                    )
                }
            }

        }
    }
}
