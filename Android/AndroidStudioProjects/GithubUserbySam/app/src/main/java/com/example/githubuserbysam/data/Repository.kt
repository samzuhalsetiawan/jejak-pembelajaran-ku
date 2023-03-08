package com.example.githubuserbysam.data

import android.util.Log
import com.example.githubuserbysam.models.User
import retrofit2.HttpException
import java.io.IOException

object Repository {
    private const val TAG = "Repository"

    private val gitHubApi by lazy { RetrofitInstance.gitHubApi }

    fun getUsersByName(name: String): List<User>? {
        val response = try {
            gitHubApi.getUsersByName(name)
        } catch (err: IOException) {
            Log.e(TAG, "getUsersByName: [IOException] ${err.message}", err)
            return null
        } catch (err: HttpException) {
            Log.e(TAG, "getUsersByName: [HttpException] ${err.message}", err)
            return null
        }
        return if (response.isSuccessful) {
            response.body()?.items ?: run {
                Log.e(TAG, "getUsersByName: [response body is null] response.body() return ${response.body()}")
                null
            }
        } else {
            Log.e(TAG, "getUsersByName: [response unSuccessful] response.isSuccessful return ${response.isSuccessful}")
            return null
        }
    }
    suspend fun test() {}
}