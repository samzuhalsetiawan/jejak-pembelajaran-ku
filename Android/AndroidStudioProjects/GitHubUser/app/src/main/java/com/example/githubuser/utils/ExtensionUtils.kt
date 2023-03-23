package com.example.githubuser.utils

import android.util.Log
import com.example.githubuser.data.models.User
import com.example.githubuser.exceptions.ApiUnexpectedResponseException
import kotlinx.coroutines.CoroutineExceptionHandler
import okio.IOException

object ExtensionUtils {

    private fun <T> List<T>.containsWithCondition(
        element: T,
        condition: T.(el: T) -> Boolean
    ): Boolean {
        return find { condition(it, element) } != null
    }

    private fun List<User>.containsUserWithId(user: User): Boolean {
        return containsWithCondition(user) { id == it.id }
    }

    fun List<User>.containsUserWithUsername(username: String): Boolean {
        return find { it.login == username } != null
    }

    fun List<User>.mapBasedOnFavoriteWith(listOfFavoriteUser: List<User>): List<User> {
        return map { it.copy() }
            .onEach { user: User ->
                user.isFavorite = listOfFavoriteUser.containsUserWithId(user)
            }
    }

    val coroutineExceptionHandler: CoroutineExceptionHandler
        get() = CoroutineExceptionHandler { _, throwable ->
            when (throwable) {
                is ApiUnexpectedResponseException -> Log.e("[coroutineExceptionHandler]", throwable.message, throwable)
                is IOException -> Log.e("[coroutineExceptionHandler]", throwable.message, throwable)
                else -> Log.e("[coroutineExceptionHandler]", throwable.message, throwable)
            }
        }

}