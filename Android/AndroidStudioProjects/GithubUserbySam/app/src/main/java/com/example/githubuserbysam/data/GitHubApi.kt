package com.example.githubuserbysam.data

import com.example.githubuserbysam.models.GetUsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/users")
    fun getUsersByName(@Query("q") name: String) : Response<GetUsersResponse>
}