package com.example.githubuser.data.sources.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import com.example.githubuser.data.models.User
import com.example.githubuser.data.sources.local.LocalService
import com.example.githubuser.data.sources.local.room.UserDB
import com.example.githubuser.data.sources.remote.RetrofitService
import com.example.githubuser.interfaces.ILocalServiceContract
import com.example.githubuser.interfaces.IRemoteServiceContract
import kotlinx.coroutines.flow.Flow

class Repository private constructor(
        private val remoteService: IRemoteServiceContract,
        private val localService: ILocalServiceContract
    ) {

    suspend fun getAllUserByName(name: String): List<User> {
        return remoteService.getAllUserByName(name)
    }

    suspend fun getAllFollowerOf(name: String): List<User> {
        return remoteService.getAllFollowerOf(name)
    }

    suspend fun getUsersFollowedBy(name: String): List<User> {
        return remoteService.getUsersFollowedBy(name)
    }

    suspend fun getDetailUser(username: String): User? {
        return remoteService.getUserByName(username)
    }
    fun getAllUserFavorite(): LiveData<List<User>> {
        return localService.getAllUserFavorite()
    }

    suspend fun addUserToFavorite(user: User) {
        localService.addUserToFavorite(user)
    }

    fun getDarkThemeEnabledPreference() : Flow<Boolean> {
        return localService.getDarkThemeEnabledPreference()
    }

    suspend fun setDarkThemeEnabledPreference(isEnabled: Boolean) {
        localService.setDarkThemeEnabledPreference(isEnabled)
    }

    companion object {

        @Volatile
        private var REPOSITORY_INSTANCE :Repository? = null

        fun getInstance(context: Context): Repository {
            return REPOSITORY_INSTANCE ?: synchronized(this) {

                val remoteService = RetrofitService.getInstance()
                val localService = LocalService.getInstance(context)

                REPOSITORY_INSTANCE ?: Repository(remoteService, localService)
                    .also { REPOSITORY_INSTANCE = it }
            }
        }
    }

}