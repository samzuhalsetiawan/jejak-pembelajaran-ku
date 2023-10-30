package com.example.storyapp.domain.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.domain.interfaces.StoriesRepository
import com.example.storyapp.presentation.home.HomeViewModel

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val storiesRepository: StoriesRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(storiesRepository) as T
        }
        return super.create(modelClass)
    }
}