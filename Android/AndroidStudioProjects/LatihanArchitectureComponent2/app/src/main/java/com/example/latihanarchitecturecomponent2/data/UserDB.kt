package com.example.latihanarchitecturecomponent2.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDB : RoomDatabase() {

    companion object {

        @Volatile
        private var instance: UserDB? = null

        fun getInstance(application: Application): UserDB {
            val tempInstance = instance
            if (tempInstance != null) return tempInstance
            synchronized(this) {
                return instance ?: run {
                    Room.databaseBuilder(application, UserDB::class.java, "db_user")
                        .fallbackToDestructiveMigration()
                        .build().also { instance = it }
                }
            }
        }
    }

    abstract fun getUserDao(): UserDao
}