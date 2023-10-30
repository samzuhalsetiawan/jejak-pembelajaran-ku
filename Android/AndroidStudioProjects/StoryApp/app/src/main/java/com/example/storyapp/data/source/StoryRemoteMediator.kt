package com.example.storyapp.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.storyapp.data.source.local.entity.StoryEntity
import com.example.storyapp.data.source.local.entity.toEntity
import com.example.storyapp.domain.interfaces.RemoteDataSource
import com.example.storyapp.domain.interfaces.StoryAppDatabase
import com.example.storyapp.domain.sealed_class.ResponseStatus
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator private constructor(
    private val remoteSource: RemoteDataSource,
    private val localSource: StoryAppDatabase
): RemoteMediator<Int, StoryEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, StoryEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> (state.lastItemOrNull()?.page ?: 0) + 1
            }
            val stories = when (val result = remoteSource.getAllStories(page, state.config.pageSize)) {
                is ResponseStatus.Error -> return MediatorResult.Error(result.throwable)
                is ResponseStatus.Success -> result.data
            }
            localSource.executeTransaction {
                if (loadType == LoadType.REFRESH) localSource.clearStories()
                localSource.upsertStories(stories.map { it.toEntity(page) })
            }
            MediatorResult.Success(endOfPaginationReached = stories.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: StoryRemoteMediator? = null

        fun getInstance(remoteSource: RemoteDataSource, localSource: StoryAppDatabase): StoryRemoteMediator {
            return INSTANCE ?: synchronized(this) {
                StoryRemoteMediator(remoteSource, localSource).also { INSTANCE = it }
            }
        }
    }
}