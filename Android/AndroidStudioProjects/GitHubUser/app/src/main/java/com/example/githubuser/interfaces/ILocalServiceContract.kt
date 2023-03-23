package com.example.githubuser.interfaces

import com.example.githubuser.data.models.User
import com.example.githubuser.sealed_class.LocalResult
import kotlinx.coroutines.flow.Flow

interface ILocalServiceContract {

    fun getUserById(userId: Int): LocalResult<Flow<User?>>

    fun getUserByName(name: String): LocalResult<Flow<List<User>>>

    fun getAllUser(): LocalResult<Flow<List<User>>>

    fun getAllUserAsList(): LocalResult<List<User>>

    suspend fun addUser(user: User): LocalResult<Unit>

    suspend fun deleteUser(user: User): LocalResult<Unit>

    fun getDarkThemeEnabledPreference(): LocalResult<Flow<Boolean>>

    suspend fun setDarkThemeEnabledPreference(isEnabled: Boolean): LocalResult<Unit>
}