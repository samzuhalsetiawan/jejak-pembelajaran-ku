package com.example.githubuser.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.githubuser.application.GitHubUserApplication
import com.example.githubuser.data.models.User
import com.example.githubuser.sealed_class.ViewModelState
import com.example.githubuser.utils.ExtensionUtils.containsUserWithUsername
import com.example.githubuser.utils.ExtensionUtils.coroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class DetailUserViewModel(username: String, application: Application): AndroidViewModel(application) {

    private val repository = (application as GitHubUserApplication).repository

    val detailUser: LiveData<ViewModelState<User>>

    private val mediatorLiveData: MediatorLiveData<Boolean> = MediatorLiveData(false)

    val isFavorite: LiveData<Boolean> = mediatorLiveData

    init {
        detailUser = liveData(viewModelScope.coroutineContext + Dispatchers.IO + coroutineExceptionHandler) {
            try {
                emit(ViewModelState.Loading())
                emit(ViewModelState.Success(repository.getDetailUser(username)))
            } catch (throwable: SocketTimeoutException) {
                emit(ViewModelState.Error("Connection Error, Coba Lagi"))
            } catch (throwable: Throwable) {
                emit(ViewModelState.Error("Unknown Error"))
                throw throwable
            }
        }

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val liveFavUser = repository.getAllFavoriteUser().asLiveData()
            withContext(Dispatchers.Main + coroutineExceptionHandler) {
                mediatorLiveData.addSource(liveFavUser) { favUser ->
                    mediatorLiveData.value = favUser.containsUserWithUsername(username)
                }
            }
        }

    }

    fun changeFavoriteStatusOfUser(user: User, favorite: Boolean) {
        val updatedUser = when (val value = detailUser.value) {
            !is ViewModelState.Success -> user
            else -> value.data
        }
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            if (favorite) {
                repository.addUserToFavorite(updatedUser)
            } else {
                repository.deleteUserFromFavorite(updatedUser)
            }
        }
    }

}