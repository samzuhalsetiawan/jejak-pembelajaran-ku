package com.example.githubuser.interfaces

import androidx.lifecycle.LiveData
import com.example.githubuser.data.models.User
import kotlinx.coroutines.flow.Flow

interface ILocalServiceContract {

    fun getAllUserFavorite(): LiveData<List<User>>

    suspend fun addUserToFavorite(user: User)

    fun getDarkThemeEnabledPreference() : Flow<Boolean>

    suspend fun setDarkThemeEnabledPreference(isEnabled: Boolean)
}