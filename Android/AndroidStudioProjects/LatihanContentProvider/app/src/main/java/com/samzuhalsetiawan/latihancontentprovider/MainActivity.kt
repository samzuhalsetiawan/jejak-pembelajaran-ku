package com.samzuhalsetiawan.latihancontentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.UserDictionary
import android.util.Log
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Query Value

        queryValue()

        //Insert Value

        val newValues = ContentValues().apply {
            put(UserDictionary.Words.APP_ID, "samzuhalsetiawan")
            put(UserDictionary.Words.LOCALE, "en_US")
            put(UserDictionary.Words.WORD, "insert")
            put(UserDictionary.Words.FREQUENCY, "100")
        }

        var newUri = contentResolver.insert(UserDictionary.Words.CONTENT_URI, newValues)

        //        Query Value again
        queryValue()

//        Update Value

        val updateValue = ContentValues().apply {
            putNull(UserDictionary.Words.LOCALE)
        }

        val selectionClauseU: String = UserDictionary.Words.LOCALE + "LIKE ?"
        val selectionArgsU: Array<String> = arrayOf("en_%")

        val rowUpdated = contentResolver.update(
            UserDictionary.Words.CONTENT_URI,
            updateValue,
            selectionClauseU,
            selectionArgsU
        )

//        Delete Value

        val selectionClauseD = "${UserDictionary.Words.APP_ID} LIKE ?"
        val selectionArgsD = arrayOf("samzuhalsetiawan")

        val rowDeleted = contentResolver.delete(UserDictionary.Words.CONTENT_URI,
            selectionClauseD,
            selectionArgsD
        )


    }

    private fun queryValue() {
        val mProjection = arrayOf(
            UserDictionary.Words._ID,
            UserDictionary.Words.WORD,
            UserDictionary.Words.LOCALE
        )
//        Anggap string ini diambil dari editText
        val selectionString: String = "insert"

        var selectionClause: String? = null

        val selectionArgs = selectionString.takeIf { it.isNotEmpty() }?.let {
            selectionClause = "${UserDictionary.Words.WORD} = ?"
            arrayOf(it)
        } ?: run {
            selectionClause = null
            emptyArray()
        }

        val mCursor = contentResolver.query(
            UserDictionary.Words.CONTENT_URI,
            mProjection,
            selectionClause,
            selectionArgs,
            null
        )

        when (mCursor?.count) {
            null -> {
                Toast.makeText(this, "mCursor count null", Toast.LENGTH_SHORT).show()
            }
            0 -> {
                Toast.makeText(this, "mCursor count 0", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "mCursor query Success", Toast.LENGTH_SHORT).show()
                displayData(mCursor)
                val data = getDataFromCursor(mCursor)
                Log.e("MainActivity", data.toString())
            }
        }

    }

    private fun getDataFromCursor(mCursor: Cursor): List<String> {
        val data = mutableListOf<String>()
        mCursor.apply {
            val index = getColumnIndex(UserDictionary.Words.WORD)

            while (moveToNext()) {
                data.add(getString(index))
            }
        }
        return data
    }

    private fun displayData(cursor: Cursor) {
        val wordListColumns = arrayOf(
            UserDictionary.Words.WORD,
            UserDictionary.Words.LOCALE
        )
        val wordListItem = intArrayOf(R.id.tvWord, R.id.tvLocale)

        val simpleCursorAdapter = SimpleCursorAdapter(
            this,
            R.layout.user_list,
            cursor,
            wordListColumns,
            wordListItem,
            0
        )

        val listView = findViewById<ListView>(R.id.lvMain)
        listView.adapter = simpleCursorAdapter

    }
}