package belajar_coroutine

import retrofit2.Response
import retrofit2.http.GET

interface JsonPlaceholderApi {

    @GET("todos")
    suspend fun getTodos(): Response<List<Todo>>
}