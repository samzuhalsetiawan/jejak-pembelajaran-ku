package com.example.githubuser.data.models

import com.google.gson.annotations.SerializedName

data class SearchUsersResponse(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    val items: List<User>
)
