package com.example.latihancoroutinepart3.Api

import com.example.latihancoroutinepart3.models.ImdbResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ImdbApi {

    @GET("en/API/Search/{apiKey}/{expression}")
    suspend fun searchMovie(@Path("apiKey") apiKey: String, @Path("expression") expression: String): Response<ImdbResponse>
}