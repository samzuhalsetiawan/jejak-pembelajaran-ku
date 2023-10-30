package com.example.storyapp.domain.interfaces

import androidx.lifecycle.LiveData
import com.example.storyapp.data.models.Story
import com.example.storyapp.data.source.UserStory
import com.example.storyapp.domain.sealed_class.ResponseStatus
import kotlinx.coroutines.flow.Flow
import java.io.File

interface StoriesRepository {

    fun getAllStories(page: Int = 0): LiveData<ResponseStatus<List<UserStory>>>

    suspend fun getAllStoriesWithLocation(): ResponseStatus<List<Story>>

    suspend fun getDetailStoryOf(storyId: String): ResponseStatus<Story>

    suspend fun registerUser(username: String, email: String, password: String): ResponseStatus<Unit>

    suspend fun loginUser(email: String, password: String): ResponseStatus<String>

    suspend fun getSavedUserToken(): ResponseStatus<Flow<String?>>

    suspend fun logout(): ResponseStatus<Unit>

    suspend fun getUsername(): ResponseStatus<String>

    suspend fun createStory(photo: File, description: String, lat: Float?, lon: Float?): ResponseStatus<Unit>

    fun setAuthenticationToken(token: String)
}