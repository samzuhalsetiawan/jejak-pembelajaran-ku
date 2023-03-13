package com.example.latihanpreferencedatastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(private val settingsPreferences: SettingsPreferences) : ViewModel() {

    fun isDarkModeEnabled(): LiveData<Boolean> {
       return settingsPreferences.isDarkModeEnabled().asLiveData()
    }

    fun setToDarkMode(darkModeEnabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsPreferences.setToDarkMode(darkModeEnabled)
        }
    }

}