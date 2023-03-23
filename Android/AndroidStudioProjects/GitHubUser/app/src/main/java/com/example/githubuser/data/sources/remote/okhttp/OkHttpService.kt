package com.example.githubuser.data.sources.remote.okhttp

import com.example.githubuser.BuildConfig
import com.example.githubuser.data.sources.remote.RemoteService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class OkHttpService private constructor() {

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
                .header("Authorization", RemoteService.API_KEY)
                .build()
            return@Interceptor chain.proceed(newRequest)
        }
    }

    val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    companion object {

        @Volatile
        private var INSTANCE: OkHttpService? = null

        fun getInstance(): OkHttpService {
            return INSTANCE ?: synchronized(this) { OkHttpService() }
        }
    }

}