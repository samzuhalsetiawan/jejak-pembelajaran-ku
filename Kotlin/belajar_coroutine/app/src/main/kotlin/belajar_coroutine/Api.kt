package belajar_coroutine

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    private val retrofit: Retrofit = Retrofit.Builder().run {
        baseUrl("https://jsonplaceholder.typicode.com")
        addConverterFactory(GsonConverterFactory.create())
        build()
    }

    val jsonPlaceholder: JsonPlaceholderApi = retrofit.create(JsonPlaceholderApi::class.java)
}