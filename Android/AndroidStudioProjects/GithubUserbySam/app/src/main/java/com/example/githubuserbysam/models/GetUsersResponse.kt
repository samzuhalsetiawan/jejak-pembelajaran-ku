package com.example.githubuserbysam.models

import com.google.gson.annotations.SerializedName

data class GetUsersResponse(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    val items: List<User>
)