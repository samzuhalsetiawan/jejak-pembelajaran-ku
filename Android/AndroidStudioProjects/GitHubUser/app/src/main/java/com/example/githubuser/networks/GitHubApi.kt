package com.example.githubuser.networks

import com.example.githubuser.models.SearchUsersResponse
import com.example.githubuser.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

    @GET("search/users")
    suspend fun getAllUserByName(@Query("q") name: String) : Response<SearchUsersResponse>

    @GET("users/{username}/followers")
    suspend fun getAllFollowerOf(@Path("username") username: String) : Response<List<User>>

    @GET("users/{username}/following")
    suspend fun getAllUserFollowedBy(@Path("username") username: String) : Response<List<User>>
}