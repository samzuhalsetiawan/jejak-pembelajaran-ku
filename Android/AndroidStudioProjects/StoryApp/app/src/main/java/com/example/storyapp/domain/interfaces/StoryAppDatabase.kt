package com.example.storyapp.domain.interfaces

import androidx.paging.PagingSource
import com.example.storyapp.data.models.User
import com.example.storyapp.data.source.local.entity.StoryEntity
import com.example.storyapp.domain.sealed_class.ResponseStatus

interface StoryAppDatabase {

    fun getUser(): ResponseStatus<User>

    suspend fun addUser(user: User): ResponseStatus<Unit>

    suspend fun clearUserTable(): ResponseStatus<Unit>
    suspend fun upsertStories(stories: List<StoryEntity>): ResponseStatus<Unit>
    fun getStory(): ResponseStatus<PagingSource<Int, StoryEntity>>
    suspend fun clearStories(): ResponseStatus<Unit>

    suspend fun <R>executeTransaction(transaction: suspend () -> R): R
}