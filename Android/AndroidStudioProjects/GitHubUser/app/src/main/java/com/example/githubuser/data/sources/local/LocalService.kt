package com.example.githubuser.data.sources.local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.example.githubuser.data.models.User
import com.example.githubuser.data.sources.local.datastore.SettingPreference
import com.example.githubuser.data.sources.local.room.UserDB
import com.example.githubuser.interfaces.ILocalServiceContract
import com.example.githubuser.utils.DebugHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class LocalService private constructor(
    private val settingPreference: SettingPreference,
    private val userDB: UserDB
) : ILocalServiceContract {

    override fun getAllUserFavorite(): LiveData<List<User>> {
        return try {
            userDB.getUserFavoriteDao().getAllUserFavorite().asFlow()
                .map {
                    it.onEach { user ->
                        user.isFavorite = true
                    }
                }.asLiveData()
        } catch (tr: Throwable) {
            DebugHelper.loggingError("LocalService::getAllUserFavorite", tr.message, tr)
            MutableLiveData()
        }
    }

    override fun getAllUserFavoriteAsList(): List<User> {
        return try {
            userDB.getUserFavoriteDao().getAllUserFavoriteAsList().onEach { it.isFavorite = true }
        } catch (tr: Throwable) {
            DebugHelper.loggingError("LocalService::getAllUserFavorite", tr.message, tr)
            emptyList()
        }
    }

    override suspend fun addUserToFavorite(user: User) {
        try {
            user.isFavorite = true
            userDB.getUserFavoriteDao().addUserToFavorite(user)
        } catch (tr: Throwable) {
            DebugHelper.loggingError("LocalService::addUserToFavorite", tr.message, tr)
        }
    }

    override suspend fun removeUserFromFavorite(user: User) {
        try {
            user.isFavorite = false
            userDB.getUserFavoriteDao().deleteUserFromFavorite(user)
        } catch (tr: Throwable) {
            DebugHelper.loggingError("LocalService::removeUserFromFavorite", tr.message, tr)
        }
    }

    override fun getDarkThemeEnabledPreference(): Flow<Boolean> {
        return settingPreference.getFlowDarkThemePreferences()
            .catch { tr ->
                DebugHelper.loggingError(
                    "LocalService::getDarkThemeEnabledPreference",
                    tr.message,
                    tr
                )
            }
    }

    override suspend fun setDarkThemeEnabledPreference(isEnabled: Boolean) {
        try {
            settingPreference.setFlowDarkThemePreferences(isEnabled)
        } catch (tr: Throwable) {
            DebugHelper.loggingError("LocalService::setDarkThemeEnabledPreference", tr.message, tr)
        }
    }

    companion object {
        @Volatile
        private var LOCAL_SERVICE_INSTANCE: LocalService? = null

        fun getInstance(context: Context): LocalService {
            return LOCAL_SERVICE_INSTANCE ?: synchronized(this) {

                val settingPreference = SettingPreference.getInstance(context)
                val userDb = UserDB.getInstance(context)

                LOCAL_SERVICE_INSTANCE ?: LocalService(
                    settingPreference,
                    userDb
                ).also { LOCAL_SERVICE_INSTANCE = it }
            }
        }
    }
}