package com.example.storyapp.domain.interfaces

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.storyapp.domain.sealed_class.ResponseStatus
import kotlinx.coroutines.flow.Flow

interface DataStorePreferences {

    fun <T> getPreferencesOf(key: Preferences.Key<T>): ResponseStatus<Flow<T?>>

    suspend fun <T> setPreferencesOf(key: Preferences.Key<T>, value: T): ResponseStatus<Unit>

    suspend fun deletePreferencesOf(key: Preferences.Key<*>): ResponseStatus<Unit>

    companion object {
        val TOKEN_KEY = stringPreferencesKey("com.example.storyapp.TOKEN_KEY")
    }
}