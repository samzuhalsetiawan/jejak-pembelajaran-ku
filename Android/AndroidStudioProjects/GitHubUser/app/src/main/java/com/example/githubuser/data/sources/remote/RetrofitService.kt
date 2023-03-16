package com.example.githubuser.data.sources.remote

import com.example.githubuser.BuildConfig
import com.example.githubuser.data.models.User
import com.example.githubuser.data.sources.remote.retrofit.GitHubApi
import com.example.githubuser.interfaces.IRemoteServiceContract
import com.example.githubuser.utils.DebugHelper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitService private constructor(retrofit: Retrofit) : IRemoteServiceContract {

    private val gitHubApi: GitHubApi = retrofit.create(GitHubApi::class.java)

    override suspend fun getAllUserByName(name: String): List<User> {
        try {
            val response = gitHubApi.getAllUserByName(name)
            if (!response.isSuccessful || response.body() == null) return emptyList()
            return response.body()?.items ?: emptyList()
        } catch (tr: Throwable) {
            DebugHelper.loggingError("RetrofitService::getAllUserByName", tr.message, tr)
            return emptyList()
        }
    }

    override suspend fun getUserByName(name: String): User? {
        try {
            val response = gitHubApi.getUserByName(name)
            if (!response.isSuccessful) return null
            return response.body()
        } catch (tr: Throwable) {
            DebugHelper.loggingError("RetrofitService::getUserByName", tr.message, tr)
            return null
        }
    }

    override suspend fun getAllFollowerOf(name: String): List<User> {
        try {
            val response = gitHubApi.getAllFollowerOf(name)
            if (!response.isSuccessful || response.body() == null) return emptyList()
            return response.body() ?: emptyList()
        } catch (tr: Throwable) {
            DebugHelper.loggingError("RetrofitService::getAllFollowerOf", tr.message, tr)
            return emptyList()
        }
    }

    override suspend fun getUsersFollowedBy(name: String): List<User> {
        try {
            val response = gitHubApi.getUsersFollowedBy(name)
            if (!response.isSuccessful || response.body() == null) return emptyList()
            return response.body() ?: emptyList()
        } catch (tr: Throwable) {
            DebugHelper.loggingError("RetrofitService::getUsersFollowedBy", tr.message, tr)
            return emptyList()
        }
    }

    companion object {
        private const val API_KEY = BuildConfig.API_KEY

        @Volatile
        private var RETROFIT_INSTANCE: RetrofitService? = null

        private val httpLoggingInterceptor by lazy {
            HttpLoggingInterceptor().setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            )
        }

        private val authInterceptor by lazy {
            Interceptor { chain ->
                val request = chain.request()
                val newRequest = request.newBuilder()
                    .header("Authorization", API_KEY)
                    .build()
                return@Interceptor chain.proceed(newRequest)
            }
        }

        private val client by lazy {
            OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(authInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()
        }

        fun getInstance(): RetrofitService {
            return RETROFIT_INSTANCE ?: synchronized(this) {
                RETROFIT_INSTANCE ?: RetrofitService(
                    Retrofit.Builder()
                        .baseUrl("https://api.github.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build()
                ).also { RETROFIT_INSTANCE = it }
            }
        }
    }
}
