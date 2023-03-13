package com.example.latihanpreferencedatastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        @Volatile
        private var INSTANCE: SettingsPreferences? = null

        private val THEME_KEY = booleanPreferencesKey("theme_key")

        fun getInstance(dataStore: DataStore<Preferences>): SettingsPreferences {
            val instance = INSTANCE
            return instance ?: synchronized(this) {
                SettingsPreferences(dataStore).also { INSTANCE = it }
            }
        }
    }

    fun isDarkModeEnabled(): Flow<Boolean> = dataStore.data.map { preference ->
        preference[THEME_KEY] ?: false
    }

    suspend fun setToDarkMode(darkModeEnabled: Boolean) {
        dataStore.edit { preference ->
            preference[THEME_KEY] = darkModeEnabled
        }
    }
}