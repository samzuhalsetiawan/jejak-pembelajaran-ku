package com.example.githubuser.data.sources.local

import com.example.githubuser.data.models.User
import com.example.githubuser.data.sources.local.datastore.SettingPreference
import com.example.githubuser.data.sources.local.db.UserDB
import com.example.githubuser.interfaces.ILocalServiceContract
import com.example.githubuser.sealed_class.LocalResult
import kotlinx.coroutines.flow.Flow

class LocalService private constructor(
    private val settingPreference: SettingPreference,
    private val userDB: UserDB
) : ILocalServiceContract {

    private val dao by lazy { userDB.getUserFavoriteDao() }

    override fun getUserById(userId: Int): LocalResult<Flow<User?>> {
        return try {
            LocalResult.ResultSuccess(dao.getUserById(userId))
        } catch (tr: Throwable) {
            LocalResult.ResultException(tr)
        }
    }

    override fun getUserByName(name: String): LocalResult<Flow<List<User>>> {
        return try {
            LocalResult.ResultSuccess(dao.getUserByName(name))
        } catch (tr: Throwable) {
            LocalResult.ResultException(tr)
        }
    }

    override fun getAllUser(): LocalResult<Flow<List<User>>> {
        return try {
            LocalResult.ResultSuccess(dao.getAllUser())
        } catch (tr: Throwable) {
            LocalResult.ResultException(tr)
        }
    }

    override fun getAllUserAsList(): LocalResult<List<User>> {
        return try {
            LocalResult.ResultSuccess(dao.getAllUserAsList())
        } catch (tr: Throwable) {
            LocalResult.ResultException(tr)
        }
    }

    override suspend fun addUser(user: User): LocalResult<Unit> {
        return try {
            user.isFavorite = true
            LocalResult.ResultSuccess(dao.addUser(user))
        } catch (tr: Throwable) {
            LocalResult.ResultException(tr)
        }
    }

    override suspend fun deleteUser(user: User): LocalResult<Unit> {
        return try {
            LocalResult.ResultSuccess(dao.deleteUser(user))
        } catch (tr: Throwable) {
            LocalResult.ResultException(tr)
        }
    }

    override fun getDarkThemeEnabledPreference(): LocalResult<Flow<Boolean>> {
        return try {
            LocalResult.ResultSuccess(settingPreference.getFlowDarkThemePreferences())
        } catch (tr: Throwable) {
            LocalResult.ResultException(tr)
        }
    }

    override suspend fun setDarkThemeEnabledPreference(isEnabled: Boolean): LocalResult<Unit> {
        return try {
            LocalResult.ResultSuccess(settingPreference.setFlowDarkThemePreferences(isEnabled))
        } catch (tr: Throwable) {
            LocalResult.ResultException(tr)
        }
    }

    companion object {
        @Volatile
        private var LOCAL_SERVICE_INSTANCE: LocalService? = null

        fun getInstance(settingPreference: SettingPreference, userDB: UserDB): LocalService {
            return LOCAL_SERVICE_INSTANCE ?: synchronized(this) {
                LocalService(settingPreference, userDB).also { LOCAL_SERVICE_INSTANCE = it }
            }
        }
    }
}