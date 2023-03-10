package com.example.githubuser.data

import android.util.Log
import com.example.githubuser.models.DetailUser
import com.example.githubuser.models.User
import com.example.githubuser.networks.RetrofitInstance
import retrofit2.HttpException
import java.io.IOException

object Repository {
    private const val TAG = "Repository"

    private val gitHubApi by lazy { RetrofitInstance.gitHubApi }

    suspend fun getAllUserByName(name: String): List<User> {
        val response = try {
            gitHubApi.getAllUserByName(name)
        } catch (err: IOException) {
            Log.e(TAG, "getAllUserByName: [IOException] ${err.message}", err)
            return emptyList()
        } catch (err: HttpException) {
            Log.e(TAG, "getAllUserByName: [HttpException] ${err.message}", err)
            return emptyList()
        }
        if (!response.isSuccessful) return emptyList<User>().also {
            Log.e(
                TAG,
                "getAllUserByName: response unSuccessful"
            )
        }

        return response.body()?.items ?: emptyList<User>().also {
            Log.e(
                TAG,
                "getAllUserByName: response body is null"
            )
        }
    }

    suspend fun getAllFollowerOf(name: String): List<User> {
        val response = try {
            gitHubApi.getAllFollowerOf(name)
        } catch (err: IOException) {
            Log.e(TAG, "getAllFollowerOf: [IOException] ${err.message}", err)
            return emptyList()
        } catch (err: HttpException) {
            Log.e(TAG, "getAllFollowerOf: [HttpException] ${err.message}", err)
            return emptyList()
        }
        if (!response.isSuccessful) return emptyList<User>().also {
            Log.e(
                TAG,
                "getAllFollowerOf: response unSuccessful"
            )
        }

        return response.body() ?: emptyList<User>().also {
            Log.e(
                TAG,
                "getAllFollowerOf: response body is null"
            )
        }
    }

    suspend fun getAllUserFollowedBy(name: String): List<User> {
        val response = try {
            gitHubApi.getAllUserFollowedBy(name)
        } catch (err: IOException) {
            Log.e(TAG, "getAllUserFollowedBy: [IOException] ${err.message}", err)
            return emptyList()
        } catch (err: HttpException) {
            Log.e(TAG, "getAllUserFollowedBy: [HttpException] ${err.message}", err)
            return emptyList()
        }
        if (!response.isSuccessful) return emptyList<User>().also {
            Log.e(
                TAG,
                "getAllUserFollowedBy: response unSuccessful"
            )
        }

        return response.body() ?: emptyList<User>().also {
            Log.e(
                TAG,
                "getAllUserFollowedBy: response body is null"
            )
        }
    }

    suspend fun getDetailUser(username: String): DetailUser? {
        val response = try {
            gitHubApi.getDetailUser(username)
        } catch (err: IOException) {
            Log.e(TAG, "getDetailUser: [IOException] ${err.message}", err)
            return null
        } catch (err: HttpException) {
            Log.e(TAG, "getDetailUser: [HttpException] ${err.message}", err)
            return null
        }
        if (!response.isSuccessful) return null.also {
            Log.e(
                TAG,
                "getDetailUser: response unSuccessful"
            )
        }

        return response.body() ?: null.also {
            Log.e(
                TAG,
                "getDetailUser: response body is null"
            )
        }
    }

}