package com.example.githubuser.interfaces

import com.example.githubuser.data.models.User

interface IRemoteServiceContract {

    suspend fun getAllUserByName(name: String): List<User>

    suspend fun getUserByName(name: String): User?

    suspend fun getAllFollowerOf(name: String): List<User>

    suspend fun getUsersFollowedBy(name: String): List<User>

}