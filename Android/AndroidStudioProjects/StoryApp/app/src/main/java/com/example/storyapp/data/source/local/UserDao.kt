package com.example.storyapp.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.storyapp.data.models.User
import com.example.storyapp.data.source.local.entity.StoryEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getUser(): List<User>

    @Insert(entity = User::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Query("DELETE FROM users")
    suspend fun deleteAllUser()

    @Upsert(entity = StoryEntity::class)
    suspend fun upsertStories(stories: List<StoryEntity>)

    @Query("SELECT * FROM story_table")
    fun getStory(): PagingSource<Int, StoryEntity>

    @Query("DELETE FROM story_table")
    suspend fun clearStories()

}