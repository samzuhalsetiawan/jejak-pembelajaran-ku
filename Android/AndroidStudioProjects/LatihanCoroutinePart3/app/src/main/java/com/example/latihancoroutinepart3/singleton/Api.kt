package com.example.latihancoroutinepart3.singleton

import com.example.latihancoroutinepart3.Api.ImdbApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {

    val IMDB_BASE_URL = "https://imdb-api.com"
    val IMDB_API_KEY = "k_4scwz8up"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().run {
            baseUrl(IMDB_BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            build()
        }
    }

    val imdbApi = retrofit.create(ImdbApi::class.java)

}