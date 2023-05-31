package com.example.storyapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("users")
data class User(
    @PrimaryKey
    @ColumnInfo("user_id")
    val userId: String,

    @ColumnInfo("name")
    val name: String,

    @ColumnInfo("token")
    val token: String
)