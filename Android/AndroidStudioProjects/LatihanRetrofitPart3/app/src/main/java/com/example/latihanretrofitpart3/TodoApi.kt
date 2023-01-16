package com.example.latihanretrofitpart3

import retrofit2.Response
import retrofit2.http.GET

interface TodoApi {

    @GET("todos")
    suspend fun getTodos(): Response<List<Todo>>

}