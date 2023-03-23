package com.example.githubuser.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.presentation.viewmodel.DetailUserViewModel

class DetailUserViewModelFactory(private val username: String,private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailUserViewModel(username, application) as T
        }
        return super.create(modelClass)
    }
}