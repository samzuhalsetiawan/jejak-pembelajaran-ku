package com.example.storyapp.domain.application

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.storyapp.data.repository.StoriesRepositoryImpl
import com.example.storyapp.data.source.StoryRemoteMediator
import com.example.storyapp.data.source.local.StoryAppDatabaseImpl
import com.example.storyapp.data.source.local.entity.StoryEntity
import com.example.storyapp.data.source.preferences.DataStorePreferencesImpl
import com.example.storyapp.data.source.remote.RemoteDataSourceImpl
import com.example.storyapp.domain.interfaces.DataStorePreferences
import com.example.storyapp.domain.interfaces.RemoteDataSource
import com.example.storyapp.domain.interfaces.StoriesRepository
import com.example.storyapp.domain.interfaces.StoryAppDatabase
import com.example.storyapp.domain.sealed_class.ResponseStatus

class UserStoryApp: Application() {

    private val dataStorePreferences: DataStorePreferences by lazy { DataStorePreferencesImpl.getInstance(this) }

    private val remoteDataSource: RemoteDataSource by lazy { RemoteDataSourceImpl.getInstance() }

    private val storyAppDatabase: StoryAppDatabase by lazy { StoryAppDatabaseImpl.getInstance(this) }

    private val storyRemoteMediator: StoryRemoteMediator by lazy { StoryRemoteMediator.getInstance(remoteDataSource, storyAppDatabase) }

    val storiesRepository: StoriesRepository by lazy { StoriesRepositoryImpl.getInstance(remoteDataSource, dataStorePreferences, storyAppDatabase) }

    init {

    }
    @OptIn(ExperimentalPagingApi::class)
    val storyPager: Pager<Int, StoryEntity> by lazy {
        Pager(
            config = PagingConfig(pageSize = 30),
            remoteMediator = storyRemoteMediator,
            pagingSourceFactory = {
                when (val result = storyAppDatabase.getStory()) {
                    is ResponseStatus.Success -> result.data
                    is ResponseStatus.Error -> throw result.throwable
                }
            }
        )
    }
}