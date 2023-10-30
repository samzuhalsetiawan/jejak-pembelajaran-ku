package com.example.storyapp.data.source.remote

import com.example.storyapp.data.source.remote.dto.CreateStoryResponseDto
import com.example.storyapp.data.source.remote.dto.GetAllStoriesResponseDto
import com.example.storyapp.data.source.remote.dto.GetDetailStoryResponseDto
import com.example.storyapp.data.source.remote.dto.LoginResponseDto
import com.example.storyapp.data.source.remote.dto.RegisterResponseDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface UserStoriesApi {

    @GET("stories")
    suspend fun getAllStories(@Query("page") page: Int, @Query("size") size: Int, @Query("location") location: Int): Response<GetAllStoriesResponseDto>

    @GET("stories/{id}")
    suspend fun getDetailStory(@Path("id") id: String): Response<GetDetailStoryResponseDto>

    @POST("login")
    @FormUrlEncoded
    suspend fun login(@Field("email") email: String, @Field("password") password: String): Response<LoginResponseDto>

    @POST("register")
    @FormUrlEncoded
    suspend fun register(@Field("name") name: String, @Field("email") email: String, @Field("password") password: String): Response<RegisterResponseDto>

    @Multipart
    @POST("stories")
    suspend fun createStory(
        @Part photo: MultipartBody.Part,
        @Part("description") description: String,
        @Part("lat") lat: Float?,
        @Part("lon") lon: Float?
    ): Response<CreateStoryResponseDto>
}