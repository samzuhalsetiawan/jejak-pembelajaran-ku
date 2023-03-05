package com.example.latihanarchitecturecomponent2.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(application)

    fun getAllUser(): LiveData<List<User>> = userRepository.getAllUser()

    fun insertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) { userRepository.insertUser(user) }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) { userRepository.deleteUser(user) }
    }

    fun deleteAllUser() {
        viewModelScope.launch(Dispatchers.IO) { userRepository.deleteAllUser() }
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) { userRepository.updateUser(user) }
    }
}