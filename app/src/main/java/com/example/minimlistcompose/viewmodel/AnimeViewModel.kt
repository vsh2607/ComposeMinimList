package com.example.minimlistcompose.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minimlistcompose.api.ApiConfig
import com.example.minimlistcompose.response.AllAnimeResponse
import com.example.minimlistcompose.response.AnimeByIdResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnimeViewModel : ViewModel() {

    private val _animeList = MutableLiveData<AllAnimeResponse>()
    val animeList: LiveData<AllAnimeResponse> = _animeList


    private var _animeDetail = MutableLiveData<AnimeByIdResponse>()
    val animeDetail : LiveData<AnimeByIdResponse> = _animeDetail


    private val _animeListFavorite = MutableLiveData<AllAnimeResponse>()
    val animeListFavorite: LiveData<AllAnimeResponse> = _animeListFavorite

    fun searchAnime(name: String) {
        ApiConfig.getApiService().searchAnime(name).enqueue(object : Callback<AllAnimeResponse> {
            override fun onResponse(call: Call<AllAnimeResponse>, response: Response<AllAnimeResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    _animeList.value = data
                    Log.d("TAG", "data live model : $data")
                } else {
                    Log.d("TAG", response.message())
                }
            }

            override fun onFailure(call: Call<AllAnimeResponse>, t: Throwable) {
                Log.d("TAG", t.message.toString())
            }
        })
    }


    fun getAnimeById(id : String){
        ApiConfig.getApiService().getAnimeById(id).enqueue(object : Callback<AnimeByIdResponse> {
            override fun onResponse(call: Call<AnimeByIdResponse>, response: Response<AnimeByIdResponse>) {
                if (response.isSuccessful) {
                    _animeDetail.value = response.body()!!
                    Log.d("TAG", animeDetail.toString())
                } else {
                    Log.d("TAG", response.message().toString())
                }
            }

            override fun onFailure(call: Call<AnimeByIdResponse>, t: Throwable) {
                Log.d("TAG", t.message.toString())
            }
        })

    }

    fun getFavorite(){
        ApiConfig.getApiService().getAllAnimeFavorited()
            .enqueue(object : Callback<AllAnimeResponse> {
                override fun onResponse(
                    call: Call<AllAnimeResponse>,
                    response: Response<AllAnimeResponse>
                ) {
                    if (response.isSuccessful) {
                        _animeListFavorite.value = response.body()!!
                    } else {
                        Log.d("TAG", response.message())
                    }
                }

                override fun onFailure(call: Call<AllAnimeResponse>, t: Throwable) {
                    Log.d("TAG", t.message.toString())
                }

            })
    }
}