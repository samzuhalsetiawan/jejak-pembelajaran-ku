package com.example.githubuser.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.githubuser.application.GitHubUserApplication
import com.example.githubuser.data.models.User
import com.example.githubuser.sealed_class.ViewModelState
import com.example.githubuser.utils.ExtensionUtils.coroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class FavoriteUserViewModel(application: Application): AndroidViewModel(application) {

    private val repository = (application as GitHubUserApplication).repository

    private val mediatorLiveData: MediatorLiveData<ViewModelState<List<User>>> = MediatorLiveData(ViewModelState.Loading())
    val listOfUser: LiveData<ViewModelState<List<User>>> = mediatorLiveData

    init {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            try {
                val favoriteUserSource = repository.getAllFavoriteUser().asLiveData()
                withContext(Dispatchers.Main + coroutineExceptionHandler) {
                    mediatorLiveData.addSource(favoriteUserSource) { listOfFavUser ->
                        if (listOfFavUser.isEmpty()) {
                            mediatorLiveData.value = ViewModelState.Error("Anda tidak memiliki user favorite")
                        } else {
                            mediatorLiveData.value = ViewModelState.Success(listOfFavUser)
                        }
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