package com.example.storyapp.data.repository

import com.example.storyapp.data.models.Story
import com.example.storyapp.domain.interfaces.DataStorePreferences
import com.example.storyapp.domain.interfaces.RemoteDataSource
import com.example.storyapp.domain.interfaces.StoriesRepository
import com.example.storyapp.domain.interfaces.StoryAppDatabase
import com.example.storyapp.domain.sealed_class.ResponseStatus
import kotlinx.coroutines.flow.Flow
import java.io.File

class StoriesRepositoryImpl private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val dataStorePreferences: DataStorePreferences,
    private val storyAppDatabase: StoryAppDatabase
    ): StoriesRepository {

    override fun setAuthenticationToken(token: String) {
        remoteDataSource.setAuthenticationToken(token)
    }

    override suspend fun getAllStories(): ResponseStatus<List<Story>> {
        return remoteDataSource.getAllStories(0, 30)
    }

    override suspend fun getDetailStoryOf(storyId: String): ResponseStatus<Story> {
        return remoteDataSource.getDetailStory(storyId)
    }

    override suspend fun getSavedUserToken(): ResponseStatus<Flow<String?>> {
        return dataStorePreferences.getPreferencesOf(DataStorePreferences.TOKEN_KEY)
    }

    override suspend fun getUsername(): ResponseStatus<String> {
        return when (val responseStatus =  storyAppDatabase.getUser()) {
            is ResponseStatus.Error -> ResponseStatus.Error(responseStatus.throwable)
            is ResponseStatus.Success -> ResponseStatus.Success(responseStatus.data.name)
        }
    }

    override suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ): ResponseStatus<Unit> {
        return remoteDataSource.register(username, email, password)
    }

    override suspend fun createStory(
        photo: File,
        description: String,
        lat: Float?,
        lon: Float?
    ): ResponseStatus<Unit> {
        return remoteDataSource.createStory(photo, description, lat, lon)
    }

    override suspend fun logout(): ResponseStatus<Unit> {
        return try {
            storyAppDatabase.clearUserTable()
            dataStorePreferences.deletePreferencesOf(DataStorePreferences.TOKEN_KEY)
            ResponseStatus.Success(Unit)
        } catch (tr: Throwable) {
            ResponseStatus.Error(tr)
        }
    }

    override suspend fun loginUser(email: String, password: String): ResponseStatus<String> {
        return when (val result = remoteDataSource.login(email, password)) {
            is ResponseStatus.Success -> {
                storyAppDatabase.clearUserTable()
                storyAppDatabase.addUser(result.data)
                dataStorePreferences.setPreferencesOf(DataStorePreferences.TOKEN_KEY, result.data.token)
                ResponseStatus.Success(result.data.name)
            }
            is ResponseStatus.Error -> ResponseStatus.Error(result.throwable)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: StoriesRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            dataStorePreferences: DataStorePreferences,
            storyAppDatabase: StoryAppDatabase): StoriesRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: StoriesRepositoryImpl(remoteDataSource, dataStorePreferences, storyAppDatabase)
            }
        }
    }
}