package com.example.githubuser.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.githubuser.application.GitHubUserApplication
import com.example.githubuser.data.models.User
import com.example.githubuser.utils.ExtensionUtils.coroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = (application as GitHubUserApplication).repository

    private val mediatorThemePreference: MediatorLiveData<Boolean> = MediatorLiveData()

    val themePreference: LiveData<Boolean> = mediatorThemePreference

    init {
        viewModelScope.launch(Dispatchers.Default + coroutineExceptionHandler) {
            val themePreferenceSource = repository.getDarkThemeEnabledPreference().asLiveData()
            withContext(Dispatchers.Main + coroutineExceptionHandler) {
                mediatorThemePreference.addSource(themePreferenceSource) { mediatorThemePreference.value = it }
            }
        }
    }

    fun changeFavoriteStatusOfUser(user: User, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            user.isFavorite = isFavorite
            if (isFavorite) {
                repository.addUserToFavorite(user)
            } else {
                repository.deleteUserFromFavorite(user)
            }
        }
    }

    fun switchDarkMode(isEnabled: Boolean) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.setDarkThemeEnabledPreference(isEnabled)
        }
    }
}