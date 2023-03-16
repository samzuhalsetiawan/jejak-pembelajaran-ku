package com.example.githubuser.data.sources.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuser.data.models.User
import com.example.githubuser.interfaces.ILocalServiceContract

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDB : RoomDatabase() {
    abstract fun getUserFavoriteDao(): UserFavoriteDao

    companion object {
        @Volatile
        private var INSTANCE : UserDB? = null

        fun getInstance(context: Context): UserDB {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context, UserDB::class.java, "user_db").build()
            }
        }
    }
}