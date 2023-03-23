package com.example.githubuser.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.enums.FollowType
import com.example.githubuser.presentation.viewmodel.FollowListViewModel

class FollowListViewModelFactory(
    private val username: String,
    private val followType: FollowType,
    private val application: Application
    ): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FollowListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FollowListViewModel(username, followType, application) as T
        }
        return super.create(modelClass)
    }

}