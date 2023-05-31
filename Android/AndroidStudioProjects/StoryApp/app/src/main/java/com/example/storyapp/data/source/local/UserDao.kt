package com.example.storyapp.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.storyapp.data.models.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getUser(): List<User>

    @Insert(entity = User::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Query("DELETE FROM users")
    suspend fun deleteAllUser()

}