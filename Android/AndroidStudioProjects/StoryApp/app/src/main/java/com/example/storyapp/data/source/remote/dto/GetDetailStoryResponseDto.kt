package com.example.storyapp.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class GetDetailStoryResponseDto(
    val error: Boolean,
    val message: String,
    @SerializedName("story")
    val storyDto: StoryDto
)