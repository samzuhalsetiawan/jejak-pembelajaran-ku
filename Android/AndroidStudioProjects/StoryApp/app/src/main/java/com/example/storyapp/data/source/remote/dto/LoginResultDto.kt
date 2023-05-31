package com.example.storyapp.data.source.remote.dto

import com.example.storyapp.data.models.User

data class LoginResultDto(
    val name: String,
    val token: String,
    val userId: String
)

fun LoginResultDto.toUser(): User {
    return User(userId, name, token)
}