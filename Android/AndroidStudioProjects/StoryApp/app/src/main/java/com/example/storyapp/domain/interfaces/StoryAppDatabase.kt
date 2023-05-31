package com.example.storyapp.domain.interfaces

import com.example.storyapp.data.models.User
import com.example.storyapp.domain.sealed_class.ResponseStatus

interface StoryAppDatabase {

    fun getUser(): ResponseStatus<User>

    suspend fun addUser(user: User): ResponseStatus<Unit>

    suspend fun clearUserTable(): ResponseStatus<Unit>
}