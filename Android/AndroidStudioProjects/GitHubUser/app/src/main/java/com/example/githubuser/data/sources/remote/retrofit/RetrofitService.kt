package com.example.githubuser.data.sources.remote.retrofit

import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

class RetrofitService private constructor(retrofit: Retrofit) {

    val gitHubApi: GitHubApi = retrofit.create(GitHubApi::class.java)

    companion object {

        @Volatile
        private var RETROFIT_INSTANCE: RetrofitService? = null

        fun getInstance(
            converterFactory: Converter.Factory,
            client: OkHttpClient? = null
        ): RetrofitService {
            return RETROFIT_INSTANCE ?: synchronized(this) {
                RETROFIT_INSTANCE ?: run {
                    val retrofitBuilder = Retrofit.Builder()
                        .baseUrl("https://api.github.com/")
                        .addConverterFactory(converterFactory)
                    if (client != null) retrofitBuilder.client(client)
                    RetrofitService(retrofitBuilder.build()).also { RETROFIT_INSTANCE = it }
                }
            }
        }
    }
}
