package com.example.githubuser.data.sources.repository

import com.example.githubuser.data.models.User
import com.example.githubuser.data.sources.local.LocalService
import com.example.githubuser.data.sources.remote.RemoteService
import com.example.githubuser.exceptions.ApiUnexpectedResponseException
import com.example.githubuser.interfaces.ILocalServiceContract
import com.example.githubuser.interfaces.IRemoteServiceContract
import com.example.githubuser.sealed_class.LocalResult
import com.example.githubuser.sealed_class.NetworkResult
import kotlinx.coroutines.flow.Flow

class Repository private constructor(
    private val remoteService: IRemoteServiceContract,
    private val localService: ILocalServiceContract
) {
    private suspend fun <T> doNetworkCall(networkCall: suspend () -> NetworkResult<T>): T {
        return when (val it = networkCall()) {
            is NetworkResult.ResultSuccess -> it.data
            is NetworkResult.ResultException -> throw it.throwable
            is NetworkResult.ResultError -> throw ApiUnexpectedResponseException(it.httpCode, it.message)
        }
    }

    private suspend fun <T> doLocalOperation(localOperation: suspend () -> LocalResult<T>): T {
        return when (val it = localOperation()) {
            is LocalResult.ResultSuccess -> it.data
            is LocalResult.ResultException -> throw it.throwable
        }
    }

    suspend fun getAllUserByName(name: String): List<User> {
        return doNetworkCall { remoteService.getAllUserByName(name) }
    }

    suspend fun getAllFollowerOf(name: String): List<User> {
        return doNetworkCall { remoteService.getAllFollowerOf(name) }
    }

    suspend fun getUsersFollowedBy(name: String): List<User> {
        return doNetworkCall { remoteService.getUsersFollowedBy(name) }
    }

    suspend fun getDetailUser(name: String): User {
        return doNetworkCall { remoteService.getUserByName(name) }
    }

    suspend fun getAllFavoriteUser(): Flow<List<User>> {
        return doLocalOperation { localService.getAllUser() }
    }

    suspend fun getAllFavoriteUserAsList(): List<User> {
        return doLocalOperation { localService.getAllUserAsList() }
    }

    suspend fun addUserToFavorite(user: User) {
        doLocalOperation { localService.addUser(user) }
    }

    suspend fun deleteUserFromFavorite(user: User) {
        doLocalOperation { localService.deleteUser(user) }
    }

    suspend fun getDarkThemeEnabledPreference(): Flow<Boolean> {
        return doLocalOperation { localService.getDarkThemeEnabledPreference() }
    }

    suspend fun setDarkThemeEnabledPreference(isEnabled: Boolean) {
        doLocalOperation { localService.setDarkThemeEnabledPreference(isEnabled) }
    }

    companion object {

        @Volatile
        private var REPOSITORY_INSTANCE: Repository? = null

        fun getInstance(localService: LocalService, remoteService: RemoteService): Repository {
            return REPOSITORY_INSTANCE ?: synchronized(this) {
                Repository(remoteService, localService).also { REPOSITORY_INSTANCE = it }
            }
        }
    }

}