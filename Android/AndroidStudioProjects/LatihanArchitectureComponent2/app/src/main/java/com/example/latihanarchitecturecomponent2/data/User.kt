package com.example.latihanarchitecturecomponent2.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "username")
    val userName: String,
    @ColumnInfo(name = "description")
    val description: String,
) {
    @ColumnInfo(name = "profile_picture")
    val profilePicture: String = "https://api.dicebear.com/5.x/big-ears-neutral/svg?seed=${userName}"
}
