package com.example.githubuser.data.sources.local.db

import androidx.room.*
import com.example.githubuser.data.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserFavoriteDao {

    @Query("SELECT * FROM user_table WHERE id = :userId")
    fun getUserById(userId: Int): Flow<User?>

    @Query("SELECT * FROM user_table WHERE login LIKE '%' || :name || '%' OR name LIKE '%' || :name || '%'")
    fun getUserByName(name: String): Flow<List<User>>

    @Query("SELECT * FROM user_table")
    fun getAllUser(): Flow<List<User>>

    @Query("SELECT * FROM user_table")
    fun getAllUserAsList(): List<User>

    @Insert(entity = User::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Delete(entity = User::class)
    suspend fun deleteUser(user: User)

}