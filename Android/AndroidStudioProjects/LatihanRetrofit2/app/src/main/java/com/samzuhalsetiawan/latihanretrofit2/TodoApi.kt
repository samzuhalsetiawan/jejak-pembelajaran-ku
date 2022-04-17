package com.samzuhalsetiawan.latihanretrofit2

import retrofit2.Call
import retrofit2.http.GET

interface TodoApi {
    @GET("/todos")
    fun getAllTodos(): Call<List<Todo>>
}