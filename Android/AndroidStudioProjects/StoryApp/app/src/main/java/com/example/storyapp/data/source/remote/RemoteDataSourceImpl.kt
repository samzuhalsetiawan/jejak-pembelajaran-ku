package com.example.storyapp.data.source.remote

import android.util.Log
import com.example.storyapp.data.models.Story
import com.example.storyapp.data.models.User
import com.example.storyapp.data.source.remote.dto.toStory
import com.example.storyapp.data.source.remote.dto.toUser
import com.example.storyapp.domain.interfaces.RemoteDataSource
import com.example.storyapp.domain.sealed_class.ResponseStatus
import com.example.storyapp.domain.utils.reduceFileImage
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File

class RemoteDataSourceImpl private constructor(): RemoteDataSource {

    override suspend fun getAllStories(page: Int, size: Int): ResponseStatus<List<Story>> {
        try {
            val response = apiService.getAllStories(page, size)
            if (!response.isSuccessful) return ResponseStatus.Error(Throwable("response unsuccessful, error code: ${response.code()}, message: ${response.message()}"))
            response.body()?.let {  getAllStoriesDto ->
                if (getAllStoriesDto.error) return ResponseStatus.Error(Throwable("Server Response with error: true"))
                val listOfStory = getAllStoriesDto.listStoryDto.map { it.toStory() }
                return ResponseStatus.Success(listOfStory)
            } ?: return ResponseStatus.Error(Throwable("Response body null"))
        } catch (tr: Throwable) {
            return ResponseStatus.Error(tr)
        }
    }

    override suspend fun getDetailStory(id: String): ResponseStatus<Story> {
        try {
            val response = apiService.getDetailStory(id)
            if (!response.isSuccessful) return ResponseStatus.Error(Throwable("response unsuccessful, error code: ${response.code()}, message: ${response.message()}"))
            response.body()?.let {  getDetailStoryDto ->
                if (getDetailStoryDto.error) return ResponseStatus.Error(Throwable("Server Response with error: true"))
                val story = getDetailStoryDto.storyDto.toStory()
                return ResponseStatus.Success(story)
            } ?: return ResponseStatus.Error(Throwable("Response body null"))
        } catch (tr: Throwable) {
            return ResponseStatus.Error(tr)
        }
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): ResponseStatus<Unit> {
        try {
            val response = apiService.register(username, email, password)
            if (!response.isSuccessful) return ResponseStatus.Error(Throwable("response unsuccessful, error code: ${response.code()}, message: ${response.message()}"))
            response.body()?.let {  registerResponseDto ->
                if (registerResponseDto.error) return ResponseStatus.Error(Throwable(registerResponseDto.message))
                return ResponseStatus.Success(Unit)
            } ?: return ResponseStatus.Error(Throwable("Response body null"))
        } catch (tr: Throwable) {
            return ResponseStatus.Error(tr)
        }
    }

    override suspend fun login(email: String, password: String): ResponseStatus<User> {
        try {
            val response = apiService.login(email, password)
            if (!response.isSuccessful) return ResponseStatus.Error(Throwable("response unsuccessful, error code: ${response.code()}, message: ${response.message()}"))
            response.body()?.let {  loginResponseDto ->
                if (loginResponseDto.error) return ResponseStatus.Error(Throwable(loginResponseDto.message))
                val user = loginResponseDto.loginResult.toUser()
                return ResponseStatus.Success(user)
            } ?: return ResponseStatus.Error(Throwable("Response body null"))
        } catch (tr: Throwable) {
            return ResponseStatus.Error(tr)
        }
    }

    override suspend fun createStory(
        photo: File,
        description: String,
        lat: Float?,
        lon: Float?
    ): ResponseStatus<Unit> {
        try {
            Log.d("MY_DEBUG", "RemoteDataSource: createStory")
            val compressedPhoto = reduceFileImage(photo)
            val photoExt = compressedPhoto.absolutePath.substring(compressedPhoto.absolutePath.lastIndexOf(". ")+1)
            val photoAsReqBody = compressedPhoto.asRequestBody("image/${photoExt.lowercase()}".toMediaTypeOrNull())
            val photoPart = MultipartBody.Part.createFormData("photo", compressedPhoto.name, photoAsReqBody)
            val response = apiService.createStory(photoPart, description, lat, lon)
            if (!response.isSuccessful) return ResponseStatus.Error(Throwable("response unsuccessful, error code: ${response.code()}, message: ${response.message()}"))
            response.body()?.let {  createStoryResponseDto ->
                if (createStoryResponseDto.error) return ResponseStatus.Error(Throwable(createStoryResponseDto.message))
                return ResponseStatus.Success(Unit)
            } ?: return ResponseStatus.Error(Throwable("Response body null"))
        } catch (tr: Throwable) {
            return ResponseStatus.Error(tr)
        }
    }

    private var token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLUFVVThQQzQ5Um5EeGdCQ2giLCJpYXQiOjE2ODIyNzY3MDV9.UiZLNFR9XZMl_LNe24NjWU8uvZRqQ9pRq-MsIE1c3Ks"

    override fun setAuthenticationToken(token: String) {
        this.token = "Bearer $token"
    }

    private val authInterceptor = Interceptor { chain ->
        val req = chain.request()
        val newReq = req.newBuilder()
            .addHeader("Authorization", token)
            .build()
        chain.proceed(newReq)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://story-api.dicoding.dev/v1/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    private val apiService = retrofit.create(UserStoriesApi::class.java)

    companion object {
        @Volatile
        private var INSTANCE: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: RemoteDataSourceImpl()
            }
        }
    }

}