package com.example.githubuser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.githubuser.data.models.User
import com.example.githubuser.data.sources.repository.Repository
import com.example.githubuser.enums.FollowType
import com.example.githubuser.exceptions.ApiIOException
import com.example.githubuser.exceptions.ApiUnexpectedResponseException
import com.example.githubuser.utils.DebugHelper
import com.example.githubuser.utils.ExtensionUtils.mapBasedOnFavoriteWith
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GitHubUserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository.getInstance(application.applicationContext)

    private val _listOfUser = MutableLiveData<List<User>>(emptyList())
    val listOfUser: LiveData<List<User>> = _listOfUser

    private val _listOfFollower = MutableLiveData<List<User>>(emptyList())
    val listOfFollower: LiveData<List<User>> = _listOfFollower

    private val _listOfFollowing = MutableLiveData<List<User>>(emptyList())
    val listOfFollowing: LiveData<List<User>> = _listOfFollowing

    private val _detailUser = MutableLiveData<User>()
    val detailUser: LiveData<User> = _detailUser

    private val _isConnectionError = MutableLiveData(false)
    val isConnectionError: LiveData<Boolean> = _isConnectionError

    private val _currentUserSearchQuery = MutableLiveData("Arif")
    val currentUserSearchQuery: LiveData<String> = _currentUserSearchQuery

    val darkThemePreference = repository.getDarkThemeEnabledPreference().asLiveData()

    fun searchUserByName(name: String) {
        viewModelScope.launch(Dispatchers.IO + networkExceptionHandler) {
            val users = repository.getAllUserByName(name)
            _listOfUser.postValue(users)
            _isConnectionError.postValue(false)
        }
    }

    fun getAllFollowerOf(name: String) {
        viewModelScope.launch(Dispatchers.IO + networkExceptionHandler) {
            val users = repository.getAllFollowerOf(name)
            _listOfFollower.postValue(users)
            _isConnectionError.postValue(false)
        }
    }

    fun getDetailUser(name: String, initial: User? = null) {
        viewModelScope.launch(Dispatchers.IO + networkExceptionHandler) {
            if (initial != null) withContext(Dispatchers.Default) {
                initial.let { _detailUser.postValue(it) }
            }
            _detailUser.postValue(repository.getDetailUser(name))
            _isConnectionError.postValue(false)
        }
    }

    fun getUsersFollowedBy(name: String) {
        viewModelScope.launch(Dispatchers.IO + networkExceptionHandler) {
            val users = repository.getUsersFollowedBy(name)
            _listOfFollowing.postValue(users)
            _isConnectionError.postValue(false)
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

    private val networkExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is ApiUnexpectedResponseException -> DebugHelper.loggingInfo(
                "networkExceptionHandler",
                throwable.message
            )
            is ApiIOException -> {
                viewModelScope.launch(Dispatchers.Default) {
                    _isConnectionError.postValue(true)
                }
            }
        }
    }

}