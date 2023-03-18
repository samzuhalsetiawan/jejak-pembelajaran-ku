package com.example.githubuser.data.sources.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.githubuser.data.models.User
import com.example.githubuser.data.sources.local.LocalService
import com.example.githubuser.data.sources.remote.RetrofitService
import com.example.githubuser.interfaces.ILocalServiceContract
import com.example.githubuser.interfaces.IRemoteServiceContract
import com.example.githubuser.utils.ExtensionUtils.mapBasedOnFavoriteWith
import kotlinx.coroutines.flow.Flow

class Repository private constructor(
        private val remoteService: IRemoteServiceContract,
        private val localService: ILocalServiceContract
    ) {

    suspend fun getAllUserByName(name: String): List<User> {
        val listOfUser = remoteService.getAllUserByName(name)
        return mapUsersWhetherTheyAreFavoriteOrNot(listOfUser)
    }

    suspend fun getAllFollowerOf(name: String): List<User> {
        val listOfUser = remoteService.getAllFollowerOf(name)
        return mapUsersWhetherTheyAreFavoriteOrNot(listOfUser)
    }

    suspend fun getUsersFollowedBy(name: String): List<User> {
        val listOfUser = remoteService.getUsersFollowedBy(name)
        return mapUsersWhetherTheyAreFavoriteOrNot(listOfUser)
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

    suspend fun removeUserFromFavorite(user: User) {
        localService.removeUserFromFavorite(user)
    }

    fun getDarkThemeEnabledPreference() : Flow<Boolean> {
        return localService.getDarkThemeEnabledPreference()
    }

    suspend fun setDarkThemeEnabledPreference(isEnabled: Boolean) {
        localService.setDarkThemeEnabledPreference(isEnabled)
    }

    private fun mapUsersWhetherTheyAreFavoriteOrNot(usersFromServer: List<User>): List<User> {
        val usersFromFavoriteList = localService.getAllUserFavoriteAsList()
        return usersFromServer.mapBasedOnFavoriteWith(usersFromFavoriteList)
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