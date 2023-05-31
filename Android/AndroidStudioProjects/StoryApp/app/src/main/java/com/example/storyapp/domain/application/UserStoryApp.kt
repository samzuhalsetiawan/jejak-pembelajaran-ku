package com.example.storyapp.domain.application

import android.app.Application
import com.example.storyapp.data.repository.StoriesRepositoryImpl
import com.example.storyapp.data.source.local.StoryAppDatabaseImpl
import com.example.storyapp.data.source.preferences.DataStorePreferencesImpl
import com.example.storyapp.data.source.remote.RemoteDataSourceImpl
import com.example.storyapp.domain.interfaces.DataStorePreferences
import com.example.storyapp.domain.interfaces.RemoteDataSource
import com.example.storyapp.domain.interfaces.StoriesRepository
import com.example.storyapp.domain.interfaces.StoryAppDatabase

class UserStoryApp: Application() {

    private val dataStorePreferences: DataStorePreferences by lazy { DataStorePreferencesImpl.getInstance(this) }

    private val remoteDataSource: RemoteDataSource by lazy { RemoteDataSourceImpl.getInstance() }

    private val storyAppDatabase: StoryAppDatabase by lazy { StoryAppDatabaseImpl.getInstance(this) }

    val storiesRepository: StoriesRepository by lazy { StoriesRepositoryImpl.getInstance(remoteDataSource, dataStorePreferences, storyAppDatabase) }

}