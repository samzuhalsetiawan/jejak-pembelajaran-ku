package com.example.storyapp.domain.interfaces

import com.example.storyapp.data.models.Story
import com.example.storyapp.data.models.User
import com.example.storyapp.domain.sealed_class.ResponseStatus
import java.io.File

interface RemoteDataSource {

    suspend fun getAllStories(page: Int = 0, size: Int = 30) : ResponseStatus<List<Story>>

    suspend fun getAllStoriesWithLocation(size: Int = 30) : ResponseStatus<List<Story>>

    suspend fun getDetailStory(id: String): ResponseStatus<Story>

    suspend fun register(username: String, email: String, password: String): ResponseStatus<Unit>

    suspend fun login(email: String, password: String): ResponseStatus<User>

    suspend fun createStory(photo: File, description: String, lat: Float?, lon: Float?): ResponseStatus<Unit>

    fun setAuthenticationToken(token: String)
}