package com.example.storyapp.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class GetAllStoriesResponseDto(
    val error: Boolean,
    val message: String,
    @SerializedName("listStory")
    val listStoryDto: List<StoryDto>
)
