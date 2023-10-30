package com.example.storyapp.data.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.example.storyapp.data.models.Story

class UserStory(
    val size: Int,
    private val pagingSourceFactory: () -> PagingSource<Int, Story>
) {
    val pager by lazy {
        Pager(
            config = PagingConfig(pageSize = size),
            pagingSourceFactory = pagingSourceFactory
        )
    }
}