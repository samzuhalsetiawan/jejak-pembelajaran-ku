package com.example.storyapp.presentation.maps

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.models.Story
import com.example.storyapp.domain.application.UserStoryApp
import com.example.storyapp.domain.sealed_class.MapsPageEvent
import com.example.storyapp.domain.sealed_class.ResponseStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapsViewModel(application: Application): AndroidViewModel(application) {

    private val userStoryApp = application as UserStoryApp
    private val storiesRepository = userStoryApp.storiesRepository

    private val _listOfStories: MutableLiveData<List<Story>> = MutableLiveData()
    val listOfStories: LiveData<List<Story>> = _listOfStories

    private val _mapsPageEvent: MediatorLiveData<MapsPageEvent> = MediatorLiveData(MapsPageEvent.OnLoading)
    val mapsPageEvent: LiveData<MapsPageEvent> = _mapsPageEvent

    init {
        _mapsPageEvent.addSource(_listOfStories) {
            _mapsPageEvent.value = MapsPageEvent.OnCloseLoading
        }
    }

    fun getAllStoriesWithLocation() {
        _mapsPageEvent.value = MapsPageEvent.OnLoading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = storiesRepository.getAllStoriesWithLocation()) {
                is ResponseStatus.Success -> {
                    val data = result.data
                    _listOfStories.postValue(data)
                }
                is ResponseStatus.Error -> _mapsPageEvent.postValue(MapsPageEvent.OnError(result.throwable))
            }
        }
    }
}