package com.example.githubuser.application

import android.app.Application
import com.example.githubuser.data.sources.local.LocalService
import com.example.githubuser.data.sources.local.datastore.SettingPreference
import com.example.githubuser.data.sources.local.db.UserDB
import com.example.githubuser.data.sources.remote.RemoteService
import com.example.githubuser.data.sources.remote.okhttp.OkHttpService
import com.example.githubuser.data.sources.remote.retrofit.RetrofitService
import com.example.githubuser.data.sources.repository.Repository
import retrofit2.converter.gson.GsonConverterFactory

class GitHubUserApplication : Application() {

    private val database by lazy { UserDB.getInstance(applicationContext) }

    private val settingPreference by lazy { SettingPreference.getInstance(applicationContext) }

    private val localService by lazy { LocalService.getInstance(settingPreference, database) }

    private val gsonConverterFactory: GsonConverterFactory by lazy { GsonConverterFactory.create() }

    private val okHttpService by lazy { OkHttpService.getInstance() }

    private val retrofitService by lazy { RetrofitService.getInstance(gsonConverterFactory, okHttpService.client) }

    private val remoteService by lazy { RemoteService.getInstance(retrofitService) }

    val repository: Repository by lazy { Repository.getInstance(localService, remoteService) }

}