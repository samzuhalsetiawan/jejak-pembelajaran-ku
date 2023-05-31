package com.example.storyapp.data.source.remote.dto

data class LoginResponseDto(
    val error: Boolean,
    val loginResult: LoginResultDto,
    val message: String
)