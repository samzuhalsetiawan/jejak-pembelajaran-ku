package com.example.storyapp.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.storyapp.domain.application.UserStoryApp
import com.example.storyapp.domain.sealed_class.MainActivityEvent
import com.example.storyapp.domain.sealed_class.ResponseStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val userStoryApp = application as UserStoryApp
    private val storiesRepository = userStoryApp.storiesRepository

    private val _event: MediatorLiveData<MainActivityEvent<Unit>> = MediatorLiveData()
    val event: LiveData<MainActivityEvent<Unit>> = _event

    private val _username: MutableLiveData<String> = MutableLiveData()
    val username: LiveData<String> = _username

    init {
        viewModelScope.launch(Dispatchers.IO) {
            when (val responseStatus = storiesRepository.getSavedUserToken()) {
                is ResponseStatus.Error -> _event.postValue(MainActivityEvent.OnError(responseStatus.throwable.message.toString()))
                is ResponseStatus.Success -> {
                    _event.addSource(responseStatus.data.asLiveData()) {
                        if (it == null) _event.postValue(MainActivityEvent.OnLogout())
                    }
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            _event.postValue(MainActivityEvent.OnLoading())
            when (val responseStatus = storiesRepository.getUsername()) {
                is ResponseStatus.Error -> _event.postValue(MainActivityEvent.OnError(responseStatus.throwable.message.toString()))
                is ResponseStatus.Success -> {
                    _username.postValue(responseStatus.data.toString())
                    _event.postValue(MainActivityEvent.OnData(Unit))
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            _event.postValue(MainActivityEvent.OnLoading())
            when (val responseStatus = storiesRepository.logout()) {
                is ResponseStatus.Error -> _event.postValue(MainActivityEvent.OnError(responseStatus.throwable.message.toString()))
                is ResponseStatus.Success -> Unit
            }
        }
    }

    fun postingStory(photo: File, story: String, lat: Float?, lon: Float?) {
        _event.value = MainActivityEvent.OnLoading()
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = storiesRepository.createStory(photo, story, lat, lon)) {
                is ResponseStatus.Success -> _event.postValue(MainActivityEvent.OnPostingSuccess())
                is ResponseStatus.Error -> { _event.postValue(MainActivityEvent.OnError(result.throwable.message.toString())) }
            }
        }
    }

}