package com.samzuhalsetiawan.latihansqlite.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.samzuhalsetiawan.latihansqlite.model.User

class MyDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {
   companion object {
       const val DATABASE_NAME = "my_database.db"
       const val VERSION = 1
       const val TABLE_NAME = "person_data"
       const val COLUMN_ID = "_id"
       const val COLUMN_NAME = "person_name"
       const val COLUMN_AGE = "person_age"
   }

    override fun onCreate(db: SQLiteDatabase?) {
        val sintaks = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT, $COLUMN_AGE INTEGER)"
        db?.execSQL(sintaks)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun addOne(user: User): Boolean {
        val db = this.writableDatabase
        val contentValue = ContentValues().apply {
            put(COLUMN_NAME, user.name)
            put(COLUMN_AGE, user.age)
        }
        val resultCode = db.insert(TABLE_NAME, null, contentValue)
        if (resultCode <= -1) return false
        return true
    }

    fun getEveryone(): List<User> {
        val result = mutableListOf<User>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        try {
            if (cursor.moveToFirst()) {
                val columnNameIndex = cursor.getColumnIndex(COLUMN_NAME)
                val columnAgeIndex = cursor.getColumnIndex(COLUMN_AGE)
                do {
                    val name = cursor.getString(columnNameIndex)
                    val age = cursor.getInt(columnAgeIndex)
                    val user = User(name, age)
                    result.add(user)
                } while (cursor.moveToNext())
            } else {
                Log.e("TAG", "First row doesn't exist")
            }
        } catch (e: Throwable) {
            Log.e("TAG", "Failed to query")
        } finally {
            cursor.close()
        }
        return result
    }

}