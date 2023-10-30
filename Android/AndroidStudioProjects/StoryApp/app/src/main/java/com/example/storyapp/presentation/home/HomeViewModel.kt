package com.example.storyapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.data.source.UserStory
import com.example.storyapp.domain.interfaces.StoriesRepository
import com.example.storyapp.domain.sealed_class.ResponseStatus

class HomeViewModel(
    private val storiesRepository: StoriesRepository
): ViewModel() {

    fun getPagerStory(page: Int = 0): LiveData<ResponseStatus<List<UserStory>>> {
        return storiesRepository.getAllStories(page)
    }

}