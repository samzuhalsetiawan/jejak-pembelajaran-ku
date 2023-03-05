package com.example.latihanarchitecturecomponent.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.latihanarchitecturecomponent.dao.NoteDbDao
import com.example.latihanarchitecturecomponent.models.Note

@Database(entities = [Note::class], version = 1, exportSchema = true)
abstract class NoteDB : RoomDatabase() {
    companion object {
        @Volatile
        private var instance: NoteDB? = null

        fun getInstance(application: Application): NoteDB {
            val tempInstance = instance
            tempInstance?.let { return it }
            synchronized(this) {
                return instance ?: run {
                    Room.databaseBuilder(
                        application,
                        NoteDB::class.java,
                        "db_note"
                    ).fallbackToDestructiveMigration().build().also { instance = it }
                }
            }
        }
    }

    abstract fun noteDbDao(): NoteDbDao
}