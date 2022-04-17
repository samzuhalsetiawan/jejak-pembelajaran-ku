package com.samzuhalsetiawan.latihanretrofit2

import com.google.gson.annotations.SerializedName

data class Todo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)
