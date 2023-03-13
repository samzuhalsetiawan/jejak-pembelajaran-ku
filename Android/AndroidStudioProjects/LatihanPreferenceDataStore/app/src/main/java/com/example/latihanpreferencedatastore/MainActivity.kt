package com.example.latihanpreferencedatastore

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settingsPreferences = SettingsPreferences.getInstance(dataStore)
        val viewModel = ViewModelProvider(this, MyFactory(settingsPreferences))[MyViewModel::class.java]

        viewModel.isDarkModeEnabled().observe(this) { enabledNightMode: Boolean ->
            AppCompatDelegate.setDefaultNightMode(
                if (enabledNightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        findViewById<SwitchCompat>(R.id.switchTheme).setOnCheckedChangeListener { _, isChecked ->
            viewModel.setToDarkMode(isChecked)
            findViewById<SwitchCompat>(R.id.switchTheme).isChecked = isChecked
        }
    }
}