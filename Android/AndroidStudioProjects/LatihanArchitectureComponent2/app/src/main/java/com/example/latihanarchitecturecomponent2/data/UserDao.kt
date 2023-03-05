package com.example.latihanarchitecturecomponent2.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table")
    fun getAllUser(): LiveData<List<User>>

    @Insert(entity = User::class)
    suspend fun insertUser(user: User)

    @Delete(entity = User::class)
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUser()

    @Update(entity = User::class)
    suspend fun updateUser(user: User)
}