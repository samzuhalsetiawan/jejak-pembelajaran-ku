package com.samzuhalsetiawan.latihanmvvm

import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.room.Room
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val users = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: JANGAN AKSES DATABASE DI MAIN THREAD ANJENK
        //TODO: PELAJARI COROUTINE IMMEDIATELY
        val db = Room.databaseBuilder(
            applicationContext,
            MyDatabase::class.java,
            "my_database.db"
        ).build()

        val userDao = db.userDao()

        findViewById<Button>(R.id.btnInsertFakeUser).setOnClickListener {
            userDao.insertMultipleUser(randUser(), randUser(), randUser())
            userDao.insertListOfUser(listOf(randUser(), randUser(), randUser()))
            userDao.insertOrUpdateIfExist(randUser())
            userDao.insertUser(randUser())
        }

        findViewById<Button>(R.id.btnGetUsers).setOnClickListener {
            users.clear()
            users.addAll(userDao.getAllUser())
            val simpleUsers = userDao.getAllUserSimple()

            Log.e("TAG", users.toString())
            Log.e("TAG", simpleUsers.toString())

        }

        findViewById<Button>(R.id.btnDeleteAll).setOnClickListener {
            userDao.deleteUser(*users.toTypedArray())
            users.clear()
        }

    }

    private fun randUser(): User {
        val hurufVocal = listOf('a', 'i', 'u', 'e', 'o')
        val hurufKonsonan = ('a'..'z').filter { it !in hurufVocal }.toList()
        val panjangNama = Random.nextInt(3, 6)

        val randomName = CharArray(panjangNama) {
            when (it % 2) {
                0 -> hurufVocal.random()
                else -> hurufKonsonan.random()
            }
        }.let { String(it) }

        val randomAge = Random.nextInt(20, 100)
        return User(randomName, randomAge)
    }

}