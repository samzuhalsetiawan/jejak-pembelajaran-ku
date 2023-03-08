package com.example.githubuser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GitHubUserViewModel : ViewModel() {

    private val _listOfUser = MutableLiveData<List<User>>()
    val listOfUser: LiveData<List<User>> = _listOfUser

    private val _listOfFollower = MutableLiveData<List<User>>()
    val listOfFollower: LiveData<List<User>> = _listOfFollower

    private val _listOfFollowing = MutableLiveData<List<User>>()
    val listOfFollowing: LiveData<List<User>> = _listOfFollowing

    fun searchUserByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val users = Repository.getAllUserByName(name)
            _listOfUser.postValue(users)
        }
    }

    fun getAllFollowerOf(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val users = Repository.getAllFollowerOf(name)
            _listOfFollower.postValue(users)
        }
    }

    fun getAllUserFollowedBy(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val users = Repository.getAllUserFollowedBy(name)
            _listOfFollowing.postValue(users)
        }
    }

}