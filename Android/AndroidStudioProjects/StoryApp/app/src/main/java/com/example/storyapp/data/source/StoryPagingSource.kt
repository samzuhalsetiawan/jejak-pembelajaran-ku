package com.example.storyapp.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.storyapp.data.models.Story
import com.example.storyapp.data.source.local.entity.StoryEntity
import com.example.storyapp.domain.interfaces.RemoteDataSource
import com.example.storyapp.domain.sealed_class.ResponseStatus

class StoryPagingSource(private val remoteDataSource: RemoteDataSource):
    PagingSource<Int, Story>()
{

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            when (val response = remoteDataSource.getAllStories(position, params.loadSize)) {
                is ResponseStatus.Error -> LoadResult.Error(response.throwable)
                is ResponseStatus.Success -> {
                    LoadResult.Page(
                        data = response.data,
                        prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                        nextKey = if (response.data.isEmpty()) null else position + 1
                    )
                }
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}