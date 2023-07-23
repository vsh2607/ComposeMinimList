package com.example.minimlistcompose.response

data class AllAnimeResponse(
	val data: List<DataItem>,
	val message: String
)

data class DataItem(
	val score: String,
	val is_favorited: String,
	val name: String,
	val genre: String,
	val ranking: String,
	val id: String,
	val synopsis: String,
	val image_thumb: String
)

