package com.example.storyapp.presentation.detail

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

class DetailViewModel(
    storyId: String,
    application: Application
): AndroidViewModel(application) {

    private val userStoryApp = application as UserStoryApp

    private val storiesRepository = userStoryApp.storiesRepository

    private val _detailStory = MutableLiveData<ViewModelState<Story>>()
    val detailStory: LiveData<ViewModelState<Story>> = _detailStory

    init {
        getDetailStoryOf(storyId)
    }

    private fun getDetailStoryOf(storyId: String) {
        _detailStory.value = ViewModelState.OnLoading()
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = storiesRepository.getDetailStoryOf(storyId)) {
                is ResponseStatus.Success -> _detailStory.postValue(ViewModelState.OnData(response.data))
                is ResponseStatus.Error -> _detailStory.postValue(ViewModelState.OnError(response.throwable.message.toString()))
            }
        }
    }

}