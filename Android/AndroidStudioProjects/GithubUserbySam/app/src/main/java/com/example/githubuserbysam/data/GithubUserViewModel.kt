package com.example.githubuserbysam.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserbysam.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GithubUserViewModel : ViewModel() {

    companion object {
        private const val TAG = "GithubUserViewModel"
    }

    private val _listOfUser = MutableLiveData<List<User>>()
    private val listOfUser: LiveData<List<User>> = _listOfUser

    fun getUsersByName(name: String): LiveData<List<User>> {
        viewModelScope.launch(Dispatchers.IO) {
            Repository.getUsersByName(name)?.let { _listOfUser.postValue(it) } ?: run {
                Log.e(TAG, "getUsersByName: [getUsersByName return null] value of Repository.getUsersByName(name) is null")
            }
        }
        return listOfUser
    }

}