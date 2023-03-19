package com.example.githubuser.data.sources.remote

import com.example.githubuser.BuildConfig
import com.example.githubuser.data.models.User
import com.example.githubuser.data.sources.remote.okhttp.OkHttpService
import com.example.githubuser.data.sources.remote.retrofit.RetrofitService
import com.example.githubuser.interfaces.IRemoteServiceContract
import com.example.githubuser.sealed_class.NetworkResult
import com.example.githubuser.utils.DebugHelper
import retrofit2.converter.gson.GsonConverterFactory

class RemoteService private constructor(
    retrofitService: RetrofitService
) : IRemoteServiceContract {

    private val gitHubApi = retrofitService.gitHubApi

    override suspend fun getAllUserByName(name: String): NetworkResult<List<User>> {
        return try {
            val response = gitHubApi.getAllUserByName(name)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.ResultSuccess(response.body()!!.items)
            } else {
                NetworkResult.ResultError(response.code(), response.message())
            }
        } catch (tr: Throwable) {
            DebugHelper.loggingError("RemoteService::getAllUserByName", tr.message, tr)
            return NetworkResult.ResultException(tr)
        }
    }

    override suspend fun getUserByName(name: String): NetworkResult<User> {
        return try {
            val response = gitHubApi.getUserByName(name)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.ResultSuccess(response.body()!!)
            } else {
                NetworkResult.ResultError(response.code(), response.message())
            }
        } catch (tr: Throwable) {
            DebugHelper.loggingError("RemoteService::getUserByName", tr.message, tr)
            return NetworkResult.ResultException(tr)
        }
    }

    override suspend fun getAllFollowerOf(name: String): NetworkResult<List<User>> {
        return try {
            val response = gitHubApi.getAllFollowerOf(name)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.ResultSuccess(response.body()!!)
            } else {
                NetworkResult.ResultError(response.code(), response.message())
            }
        } catch (tr: Throwable) {
            DebugHelper.loggingError("RemoteService::getAllFollowerOf", tr.message, tr)
            return NetworkResult.ResultException(tr)
        }
    }

    override suspend fun getUsersFollowedBy(name: String): NetworkResult<List<User>> {
        return try {
            val response = gitHubApi.getUsersFollowedBy(name)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.ResultSuccess(response.body()!!)
            } else {
                NetworkResult.ResultError(response.code(), response.message())
            }
        } catch (tr: Throwable) {
            DebugHelper.loggingError("RemoteService::getAllFollowerOf", tr.message, tr)
            return NetworkResult.ResultException(tr)
        }
    }

    companion object {

        const val API_KEY = BuildConfig.API_KEY

        @Volatile
        private var INSTANCE: RemoteService? = null

        fun getInstance(): RemoteService {
            return INSTANCE ?: synchronized(this) {
                val gsonConverter = GsonConverterFactory.create()
                val okHttpService = OkHttpService.getInstance()
                val retrofitService =
                    RetrofitService.getInstance(gsonConverter, okHttpService.client)
                INSTANCE ?: RemoteService(retrofitService)
            }
        }

    }

}