package com.example.storyapp.domain.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.presentation.detail.DetailViewModel

class DetailViewModelFactory(
    private val storyId: String,
    private val application: Application
): ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(storyId, application) as T
        } else {
            return super.create(modelClass)
        }
    }

}