package com.example.githubuser.data.sources.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuser.data.models.User

@Dao
interface UserFavoriteDao {

    @Query("SELECT * FROM user_favorite_table")
    fun getAllUserFavorite(): LiveData<List<User>>

    @Query("SELECT * FROM user_favorite_table")
    fun getAllUserFavoriteAsList(): List<User>

    @Insert(entity = User::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserToFavorite(user: User)

    @Delete(entity = User::class)
    suspend fun deleteUserFromFavorite(user: User)
}