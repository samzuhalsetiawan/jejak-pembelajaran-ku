package com.example.latihanlocaldatapersistence

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.latihanlocaldatapersistence.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            btnSaveInternal.setOnClickListener { startActivity(Intent(this@MainActivity, LatihanSaveIntoInternal::class.java)) }
            btnSharedPreferences.setOnClickListener { startActivity(Intent(this@MainActivity, LatihanSharedPreferences::class.java)) }
            btnDataStore.setOnClickListener { startActivity(Intent(this@MainActivity, LatihanDataStorePreferences::class.java)) }
            btnRoomDatabase.setOnClickListener { startActivity(Intent(this@MainActivity, LatihanRoomDatabase::class.java)) }
            btnRepoAndInjection.setOnClickListener { startActivity(Intent(this@MainActivity, LatihanRepositoryAndInjection::class.java)) }
        }

    }

}