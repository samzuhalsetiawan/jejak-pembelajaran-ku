package com.example.githubuser.interfaces

import com.example.githubuser.data.models.User
import com.example.githubuser.sealed_class.NetworkResult

interface IRemoteServiceContract {

    suspend fun getAllUserByName(name: String): NetworkResult<List<User>>

    suspend fun getUserByName(name: String): NetworkResult<User>

    suspend fun getAllFollowerOf(name: String): NetworkResult<List<User>>

    suspend fun getUsersFollowedBy(name: String): NetworkResult<List<User>>

}