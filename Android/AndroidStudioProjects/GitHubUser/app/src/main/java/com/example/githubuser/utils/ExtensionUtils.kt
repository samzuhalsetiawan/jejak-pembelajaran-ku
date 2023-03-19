package com.example.githubuser.utils

import com.example.githubuser.data.models.User

object ExtensionUtils {

    private fun <T> List<T>.containsWithCondition(
        element: T,
        condition: T.(el: T) -> Boolean
    ): Boolean {
        return find { condition(it, element) } != null
    }

    fun <T> List<T>.containsAndDistinctConditionAll(
        list2: List<T>,
        condition: (it: T) -> Boolean
    ): Boolean {
        val isContainsAll = containsAll(list2)
        val isConditionAccepted = fold(false) { _: Boolean, el: T ->
            if (list2.contains(el)) condition(el) else !condition(el)
        }
        return isContainsAll and isConditionAccepted
    }

    fun List<User>.mapBasedOnFavoriteWith(listOfFavoriteUser: List<User>): List<User> {
        return map { it.copy() }
            .onEach { user: User ->
                user.isFavorite = listOfFavoriteUser.containsWithCondition(user) { id == it.id }
            }
    }

}