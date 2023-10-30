package com.example.storyapp.presentation.home.util

import com.example.storyapp.data.models.Story
import com.example.storyapp.data.source.UserStory
import com.example.storyapp.data.source.StoryPagingSource
import com.example.storyapp.data.source.remote.RemoteDataSourceImpl

object DummyDataTest {
    fun dummyStory(amount: Int) = List(amount) {
        Story(
            id = "id$it",
            name = "name$it",
            description = "desc$it",
            photoUrl = "url$it",
            createdAt = "date$it",
            storyPosition = null
        )
    }

    fun List<Story>.toStoryPager(): UserStory {
        return UserStory(
            size = 30,
            pagingSourceFactory = {
                StoryPagingSource(RemoteDataSourceImpl.getImplementation())
            }
        )
    }
}