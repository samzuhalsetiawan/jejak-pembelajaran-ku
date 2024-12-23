package com.wahyusembiring.roomksp2.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wahyusembiring.roomksp2.domain.model.Homework

@TypeConverters(Converter::class)
@Database(
   entities = [
      Homework::class
   ],
   version = 1,
   exportSchema = false
)
abstract class MainDatabase : RoomDatabase() {

   abstract fun homeworkDao(): HomeworkDao
//   abstract val subjectDao: SubjectDao

   companion object {
      private const val DATABASE_NAME = "habit.db"

      @Volatile
      private var INSTANCE: MainDatabase? = null

      fun getSingleton(appContext: Context): MainDatabase {
         return INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
               appContext,
               MainDatabase::class.java,
               DATABASE_NAME
            )
               .addTypeConverter(Converter())
               .fallbackToDestructiveMigration()
               .build().also { INSTANCE = it }
         }
      }

   }

}