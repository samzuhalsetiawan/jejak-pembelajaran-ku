package com.example.storyapp.data.source.local

import android.app.Application
import androidx.paging.PagingSource
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.withTransaction
import com.example.storyapp.data.models.User
import com.example.storyapp.data.source.local.entity.StoryEntity
import com.example.storyapp.domain.interfaces.StoryAppDatabase
import com.example.storyapp.domain.sealed_class.ResponseStatus

@Database(entities = [User::class, StoryEntity::class], version = 1, exportSchema = false)
abstract class StoryAppDatabaseImpl: RoomDatabase(), StoryAppDatabase {

    abstract fun userDao(): UserDao

    override suspend fun addUser(user: User): ResponseStatus<Unit> {
        return try {
            userDao().addUser(user)
            ResponseStatus.Success(Unit)
        } catch (tr: Throwable) {
            ResponseStatus.Error(tr)
        }
    }

    override suspend fun upsertStories(stories: List<StoryEntity>): ResponseStatus<Unit> {
        return try {
            userDao().upsertStories(stories)
            ResponseStatus.Success(Unit)
        } catch (tr: Throwable) {
            ResponseStatus.Error(tr)
        }
    }

    override fun getStory(): ResponseStatus<PagingSource<Int, StoryEntity>> {
        return try {
            ResponseStatus.Success(userDao().getStory())
        } catch (tr: Throwable) {
            ResponseStatus.Error(tr)
        }
    }

    override suspend fun clearStories(): ResponseStatus<Unit> {
        return try {
            userDao().clearStories()
            ResponseStatus.Success(Unit)
        } catch (tr: Throwable) {
            ResponseStatus.Error(tr)
        }
    }

    override suspend fun <R> executeTransaction(transaction: suspend () -> R): R {
        return withTransaction { transaction() }
    }

    override suspend fun clearUserTable(): ResponseStatus<Unit> {
        return try {
            userDao().deleteAllUser()
            ResponseStatus.Success(Unit)
        } catch (tr: Throwable) {
            ResponseStatus.Error(tr)
        }
    }

    override fun getUser(): ResponseStatus<User> {
        return try {
            val result = userDao().getUser()
            if (result.isEmpty()) {
                ResponseStatus.Error(Throwable("User Not Found"))
            } else {
                ResponseStatus.Success(result[0])
            }
        } catch (tr: Throwable) {
            ResponseStatus.Error(tr)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: StoryAppDatabase? = null

        private const val DB_NAME = "story_app_db"

        fun getInstance(application: Application): StoryAppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    application.applicationContext,
                    StoryAppDatabaseImpl::class.java,
                    DB_NAME
                ).build()
            }
        }
    }
}