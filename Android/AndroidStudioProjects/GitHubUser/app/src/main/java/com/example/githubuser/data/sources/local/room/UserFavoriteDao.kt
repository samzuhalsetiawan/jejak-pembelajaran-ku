package com.example.githubuser.data.sources.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubuser.data.models.User

@Dao
interface UserFavoriteDao {

    @Query("SELECT * FROM user_favorite_table")
    fun getAllUserFavorite(): LiveData<List<User>>

    @Insert(entity = User::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserToFavorite(user: User)
}