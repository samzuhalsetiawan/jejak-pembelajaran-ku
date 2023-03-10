package com.example.githubuser.networks

import com.example.githubuser.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private const val API_KEY = BuildConfig.API_KEY

    private val retrofit: Retrofit by lazy {
        val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        )

        val authInterceptor = Interceptor { chain ->
            val request = chain.request()
            val newRequest = request.newBuilder()
                .header("Authorization", API_KEY)
                .build()
            return@Interceptor chain.proceed(newRequest)
        }

        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()

        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val gitHubApi: GitHubApi by lazy { retrofit.create(GitHubApi::class.java) }
}