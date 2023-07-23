package com.example.minimlistcompose.api

import com.example.minimlistcompose.response.AllAnimeResponse
import com.example.minimlistcompose.response.AnimeByIdResponse
import com.example.minimlistcompose.response.FavoriteResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {


    @GET("get_all_anime_favorited")
    fun getAllAnimeFavorited() : Call<AllAnimeResponse>

    @GET("get_anime_by_id/{id}")
    fun getAnimeById(@Path("id") id: String): Call<AnimeByIdResponse>

    @FormUrlEncoded
    @POST("add_favorite")
    fun addFavorite(  @Field("anime_id") anime_id: String): Call<FavoriteResponse>

    @DELETE("delete_favorite/{id}")
    fun deleteFavorite(@Path("id") id: String): Call<FavoriteResponse>

    @FormUrlEncoded
    @POST("search_anime")
    fun searchAnime(  @Field("anime_name") anime_name: String): Call<AllAnimeResponse>


}