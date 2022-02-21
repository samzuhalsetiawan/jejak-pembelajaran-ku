package com.samzuhalsetiawan.latihanaksescontentprovider

import android.Manifest
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.UserDictionary
import androidx.recyclerview.widget.LinearLayoutManager
import com.samzuhalsetiawan.latihanaksescontentprovider.adapter.DictionaryAdapter
import com.samzuhalsetiawan.latihanaksescontentprovider.databinding.ActivityMainBinding
import com.samzuhalsetiawan.latihanaksescontentprovider.model.Dictionary
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    private val dictionaryList by lazy { ArrayList<Dictionary>() }
    private val dictionaryAdapter by lazy { DictionaryAdapter(dictionaryList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        insertDictionary()

        binding.rvUserDictionary.layoutManager = LinearLayoutManager(this)
        binding.rvUserDictionary.adapter = dictionaryAdapter

        binding.btnGrantPermission.setOnClickListener {
            println(queryAllDictionary().toList())
        }

    }

    private fun insertDictionary() {

        val contentValues = ContentValues().apply {
            put(UserDictionary.Words.APP_ID, "example.user")
            put(UserDictionary.Words.WORD, "HalloDunia")
            put(UserDictionary.Words.LOCALE, Locale.getDefault().toString())
            put(UserDictionary.Words.FREQUENCY, "100")
        }

        contentResolver.insert(
            UserDictionary.Words.CONTENT_URI,
            contentValues
        )
    }

    private fun queryAllDictionary(): ArrayList<Dictionary> {
        println("=======Do QUERY========")
        val result = ArrayList<Dictionary>()
        val mCursor = contentResolver.query(
            UserDictionary.Words.CONTENT_URI,
            arrayOf(
                UserDictionary.Words._ID,
                UserDictionary.Words.WORD,
                UserDictionary.Words.LOCALE,
                UserDictionary.Words.APP_ID,
                UserDictionary.Words.FREQUENCY
            ),
            null,
            null,
            null
        )

        mCursor?.apply {
            val idIndex = getColumnIndex(UserDictionary.Words._ID)
            val idWord = getColumnIndex(UserDictionary.Words.WORD)
            val idLocale = getColumnIndex(UserDictionary.Words.LOCALE)
            val idAppId = getColumnIndex(UserDictionary.Words.APP_ID)
            val idFrequency = getColumnIndex(UserDictionary.Words.FREQUENCY)
            while (moveToNext()) {
                println("=======MOVE NEXT========")
                val dictionary = Dictionary(
                    getString(idIndex),
                    getString(idWord),
                    getString(idLocale),
                    getString(idAppId),
                    getString(idFrequency)
                )
                result.add(dictionary)
            }
        } ?: run {
            println("===========CURSOR IS NULL==========")
        }
        return result
    }
}