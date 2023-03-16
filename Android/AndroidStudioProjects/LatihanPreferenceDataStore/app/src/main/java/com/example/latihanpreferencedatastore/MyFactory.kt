package com.example.latihanpreferencedatastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyFactory(private val settingsPreferences: SettingsPreferences) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            return MyViewModel(settingsPreferences) as T
        } else {
            throw Exception("Wrong Class: got ${modelClass.simpleName} as a class but ${MyViewModel::class.java.simpleName} was expected")
        }
    }
}