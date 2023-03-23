package com.example.githubuser.data.sources.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreference private constructor(context: Context) {

    private val Context.dataStorePreference: DataStore<Preferences> by preferencesDataStore("settings")

    private val dataStorePreference = context.dataStorePreference

    fun getFlowDarkThemePreferences(): Flow<Boolean> {
        return dataStorePreference.data.map { preferences ->
            preferences[DARK_THEME_KEY] ?: false
        }
    }

    suspend fun setFlowDarkThemePreferences(darkModeEnabled: Boolean) {
        dataStorePreference.edit { preferences ->
            preferences[DARK_THEME_KEY] = darkModeEnabled
        }
    }

    companion object {

        private val DARK_THEME_KEY = booleanPreferencesKey("com.example.githubuser.DARK_THEME_KEY")

        @Volatile
        private var INSTANCE: SettingPreference? = null

        fun getInstance(context: Context): SettingPreference {
            return INSTANCE ?: synchronized(this) {
                SettingPreference(context).also { INSTANCE = it }
            }
        }
    }

}