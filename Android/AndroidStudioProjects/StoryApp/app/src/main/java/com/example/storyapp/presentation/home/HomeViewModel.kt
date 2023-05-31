package com.example.storyapp.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.models.Story
import com.example.storyapp.domain.application.UserStoryApp
import com.example.storyapp.domain.sealed_class.ResponseStatus
import com.example.storyapp.domain.sealed_class.ViewModelState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private val userStoryApp = application as UserStoryApp

    private val storiesRepository = userStoryApp.storiesRepository

    private val _listOfStories = MutableLiveData<ViewModelState<List<Story>>>()
    val listOfStories: LiveData<ViewModelState<List<Story>>> = _listOfStories

    init {
        getListOfStories()
    }

    private fun getListOfStories() {
        _listOfStories.value = ViewModelState.OnLoading()
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = storiesRepository.getAllStories()) {
                is ResponseStatus.Success -> _listOfStories.postValue(ViewModelState.OnData(response.data))
                is ResponseStatus.Error -> _listOfStories.postValue(ViewModelState.OnError(response.throwable.message.toString()))
            }
        }
    }

}