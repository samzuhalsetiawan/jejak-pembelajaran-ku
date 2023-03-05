package com.example.latihanarchitecturecomponent2.data

import android.app.Application

class UserRepository(application: Application) {

    private val userDao = UserDB.getInstance(application).getUserDao()

    fun getAllUser() = userDao.getAllUser()

    suspend fun insertUser(user: User) = userDao.insertUser(user)

    suspend fun deleteUser(user: User) = userDao.deleteUser(user)

    suspend fun deleteAllUser() = userDao.deleteAllUser()

    suspend fun updateUser(user: User) = userDao.updateUser(user)
}