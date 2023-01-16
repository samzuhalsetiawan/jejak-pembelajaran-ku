package com.example.latihanretrofitpart3

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().run {
            baseUrl("https://jsonplaceholder.typicode.com")
            addConverterFactory(GsonConverterFactory.create())
            build()
        }
    }
    val todoApi = retrofit.create(TodoApi::class.java)
}