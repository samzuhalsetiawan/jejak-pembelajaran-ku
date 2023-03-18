package com.example.githubuser.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.*
import com.example.githubuser.data.sources.local.datastore.SettingPreference
import com.example.githubuser.enums.FollowType
import com.example.githubuser.data.models.User
import com.example.githubuser.data.sources.repository.Repository
import com.example.githubuser.utils.ExtensionUtils.mapBasedOnFavoriteWith
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GitHubUserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository.getInstance(application.applicationContext)

    private val _listOfUser = MutableLiveData<List<User>>()
    val listOfUser: LiveData<List<User>> = _listOfUser

    private val _listOfFollower = MutableLiveData<List<User>>()
    val listOfFollower: LiveData<List<User>> = _listOfFollower

    private val _listOfFollowing = MutableLiveData<List<User>>()
    val listOfFollowing: LiveData<List<User>> = _listOfFollowing

    private val _detailUser = MutableLiveData<User>()
    val detailUser: LiveData<User> = _detailUser

    val darkThemePreference = repository.getDarkThemeEnabledPreference().asLiveData()

    fun searchUserByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val users = repository.getAllUserByName(name)
            _listOfUser.postValue(users)
        }
    }

    fun getAllFollowerOf(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val users = repository.getAllFollowerOf(name)
            _listOfFollower.postValue(users)
        }
    }

    fun getDetailUser(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.getDetailUser(name)
            user?.let { _detailUser.postValue(it) }
        }
    }

    fun getUsersFollowedBy(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val users = repository.getUsersFollowedBy(name)
            _listOfFollowing.postValue(users)
        }
    }

    fun setDarkModeEnabled(isEnabled: Boolean) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.setDarkThemeEnabledPreference(isEnabled)
        }
    }

    fun getAllUserFavorite(): LiveData<List<User>> {
        return repository.getAllUserFavorite()
    }

    fun addUserToFavorite(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            user.isFavorite = true
            repository.addUserToFavorite(user)
        }
    }

    fun removeUserFromFavorite(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            user.isFavorite = false
            repository.removeUserFromFavorite(user)
        }
    }

    fun getListOfUserBasedFollowType(followType: FollowType): LiveData<List<User>> {
        return when (followType) {
            FollowType.Follower -> listOfFollower
            FollowType.Following -> listOfFollowing
        }
    }

    fun clearListOfFollowerAndFollowing() {
        viewModelScope.launch(Dispatchers.Default) {
            _listOfFollower.postValue(emptyList())
            _listOfFollowing.postValue(emptyList())
        }
    }

    fun notifyFavoriteUserChange(listOfFavorite: List<User>) {
        val oldListOfUser = listOfUser.value ?: emptyList()
        val oldFollowers = listOfFollower.value ?: emptyList()
        val oldFollowing = listOfFollowing.value ?: emptyList()

        viewModelScope.launch(Dispatchers.Default) {
            _listOfUser.postValue(
                oldListOfUser.mapBasedOnFavoriteWith(listOfFavorite)
            )
            _listOfFollower.postValue(
                oldFollowers.mapBasedOnFavoriteWith(listOfFavorite)
            )
            _listOfFollowing.postValue(
                oldFollowing.mapBasedOnFavoriteWith(listOfFavorite)
            )
        }
    }

}