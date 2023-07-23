package com.example.minimlistcompose.response

data class AnimeByIdResponse(
	val data: Data,
	val message: String
)

data class Data(
	val score: String,
	val is_favorited: String,
	val name: String,
	val genre: String,
	val ranking: String,
	val id: String,
	val synopsis: String,
	val image_thumb: String
)

