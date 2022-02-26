package com.samzuhalsetiawan.latihanmvvm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @ColumnInfo(name = "user_name") val name: String,
    @ColumnInfo(name = "user_age") val age: Int
) {
    @PrimaryKey(autoGenerate = true) var uid: Int? = null
}
