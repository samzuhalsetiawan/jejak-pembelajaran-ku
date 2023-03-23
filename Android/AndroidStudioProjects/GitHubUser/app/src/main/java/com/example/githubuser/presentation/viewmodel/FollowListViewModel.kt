package com.example.githubuser.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.githubuser.application.GitHubUserApplication
import com.example.githubuser.data.models.User
import com.example.githubuser.enums.FollowType
import com.example.githubuser.sealed_class.ViewModelState
import com.example.githubuser.utils.ExtensionUtils.coroutineExceptionHandler
import com.example.githubuser.utils.ExtensionUtils.mapBasedOnFavoriteWith
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class FollowListViewModel(username: String, followType: FollowType, application: Application): AndroidViewModel(application) {

    private val repository = (application as GitHubUserApplication).repository

    private val mediatorLiveData = MediatorLiveData<ViewModelState<List<User>>>(ViewModelState.Loading())

    val listOfUser: LiveData<ViewModelState<List<User>>> = mediatorLiveData

    init {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            try {
                val result = when (followType) {
                    FollowType.Follower -> repository.getAllFollowerOf(username)
                    FollowType.Following -> repository.getUsersFollowedBy(username)
                }
                withContext(Dispatchers.Main + coroutineExceptionHandler) {
                    if (result.isEmpty()) mediatorLiveData.value = when (followType) {
                        FollowType.Follower -> ViewModelState.Error("User tidak memiliki follower")
                        FollowType.Following -> ViewModelState.Error("User tidak memfollow siapapun")
                    } else mediatorLiveData.value = ViewModelState.Success(result)
                }
                val listOfFavUserSource = repository.getAllFavoriteUser().asLiveData()
                withContext(Dispatchers.Main + coroutineExceptionHandler) {
                    mediatorLiveData.addSource(listOfFavUserSource) { listOfFavUser ->
                        val currentValue = mediatorLiveData.value ?: return@addSource
                        if (currentValue !is ViewModelState.Success) return@addSource
                        val updatedList = currentValue.data.mapBasedOnFavoriteWith(listOfFavUser)
                        mediatorLiveData.value = ViewModelState.Success(updatedList)
                    }
                }
            } catch (throwable: SocketTimeoutException) {
                mediatorLiveData.postValue(ViewModelState.Error("Connection Error, Coba Lagi"))
            } catch (throwable: Throwable) {
                mediatorLiveData.postValue(ViewModelState.Error("Unknown Error"))
                throw throwable
            }
        }
    }

}