package com.example.storyapp.data.source.preferences

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.storyapp.domain.interfaces.DataStorePreferences
import com.example.storyapp.domain.sealed_class.ResponseStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreferencesImpl private constructor(private val application: Application) : DataStorePreferences {

    private val Context.datastorePreferences: DataStore<Preferences> by preferencesDataStore("preferences")

    override fun <T> getPreferencesOf(key: Preferences.Key<T>): ResponseStatus<Flow<T?>> {
        return try {
            val result = application.applicationContext.datastorePreferences.data.map { preferences ->
                preferences[key]
            }
            ResponseStatus.Success(result)
        } catch (tr: Throwable) {
            ResponseStatus.Error(tr)
        }
    }

    override suspend fun <T> setPreferencesOf(key: Preferences.Key<T>, value: T): ResponseStatus<Unit> {
        return try {
            application.applicationContext.datastorePreferences.edit { mutablePreferences ->
                mutablePreferences[key] = value
            }
            ResponseStatus.Success(Unit)
        } catch (tr: Throwable) {
            ResponseStatus.Error(tr)
        }
    }

    override suspend fun deletePreferencesOf(key: Preferences.Key<*>): ResponseStatus<Unit> {
        return try {
            application.applicationContext.datastorePreferences.edit { mutablePreferences ->
                mutablePreferences.remove(key)
            }
            ResponseStatus.Success(Unit)
        } catch (tr: Throwable) {
            ResponseStatus.Error(tr)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DataStorePreferences? = null

        fun getInstance(application: Application): DataStorePreferences {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DataStorePreferencesImpl(application)
            }
        }
    }
}