package com.samzuhalsetiawan.latihanretrofit

import retrofit2.Response
import retrofit2.http.GET

interface TodoApi {
    @GET("/todos")
    fun getAllTodo(): Response<List<Todo>>
}