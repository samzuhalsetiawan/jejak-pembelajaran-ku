package com.example.latihanlocaldatapersistence

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.*
import com.example.latihanlocaldatapersistence.databinding.ActivityLatihanDataStorePreferencesBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class LatihanDataStorePreferences : AppCompatActivity() {

    private val binding: ActivityLatihanDataStorePreferencesBinding by lazy { ActivityLatihanDataStorePreferencesBinding.inflate(layoutInflater) }
    private lateinit var dataStorePreference: DataStorePreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dataStorePreference = DataStorePreference(applicationContext)

        binding.btnSave.setOnClickListener { saveIntoDataStorePreference() }
        binding.btnOpen.setOnClickListener { readFromDataStorePreference() }
    }

    private fun saveIntoDataStorePreference() {
        val key = stringPreferencesKey(binding.etKey.text.toString())
        val value = binding.etValue.text.toString()

        lifecycleScope.launch {
            dataStorePreference.writeIntoDataStorePreference(key, value)
        }
    }

    private fun readFromDataStorePreference() {
        val key = stringPreferencesKey(binding.etKey.text.toString())
        val value = dataStorePreference.readFromDataStorePreference(key, "")
        value.asLiveData().observe(this) {
            binding.tvValue.text = it
        }
    }
}

class DataStorePreference(context: Context) {

    private val Context.dataStorePreference: DataStore<Preferences> by preferencesDataStore(DATASTORE_PREF_NAME)
    private val dataStorePreference = context.dataStorePreference

    suspend fun <T> writeIntoDataStorePreference(key: Preferences.Key<T>, value: T) {
        dataStorePreference.edit { preferences ->
            preferences[key] = value
        }
    }

    fun <T> readFromDataStorePreference(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return dataStorePreference.data.map { preferences ->
            preferences[key] ?: defaultValue
        }
    }

    companion object {
        private const val DATASTORE_PREF_NAME = "com.example.latihanlocaldatapersistence.DATASTORE_PREF_NAME"

        @Volatile
        private var INSTANCE: DataStorePreference? = null

        fun getInstance(context: Context): DataStorePreference {
            return INSTANCE ?: synchronized(this) {
                DataStorePreference(context)
            }
        }
    }



}