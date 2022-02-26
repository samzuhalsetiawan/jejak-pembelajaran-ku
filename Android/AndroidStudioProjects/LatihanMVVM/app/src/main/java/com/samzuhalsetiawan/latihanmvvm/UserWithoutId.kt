package com.samzuhalsetiawan.latihanmvvm

import androidx.room.ColumnInfo

data class UserWithoutId(
    @ColumnInfo(name = "user_name") val name: String,
    @ColumnInfo(name = "user_age") val age: Int
)
