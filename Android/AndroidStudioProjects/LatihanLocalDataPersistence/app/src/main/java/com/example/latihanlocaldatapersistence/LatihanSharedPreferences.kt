package com.example.latihanlocaldatapersistence

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.latihanlocaldatapersistence.databinding.ActivityLatihanSharedPreferencesBinding

class LatihanSharedPreferences : AppCompatActivity() {

    companion object {
        private const val SHARE_PREF_NAME = "com.example.latihanlocaldatapersistence.SHARE_PREF_NAME"
    }

    private val binding: ActivityLatihanSharedPreferencesBinding by lazy { ActivityLatihanSharedPreferencesBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener { saveIntoSharedPreference() }
        binding.btnOpen.setOnClickListener { readFromSharedPreference() }

    }

    private fun saveIntoSharedPreference() {
        val key = binding.etKey.text.toString()
        val value = binding.etValue.text.toString()

        val sharedPref = getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE)
        sharedPref.edit()
            .putString(key, value)
            .apply()

        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
    }

    private fun readFromSharedPreference() {
        val key = binding.etKey.text.toString()

        val sharedPref = getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE)
        val value = sharedPref.getString(key, "")

        binding.tvValue.text = value
    }

}