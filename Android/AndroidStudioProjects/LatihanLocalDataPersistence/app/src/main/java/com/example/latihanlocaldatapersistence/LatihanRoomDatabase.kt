package com.example.latihanlocaldatapersistence

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.latihanlocaldatapersistence.databinding.ActivityLatihanRoomDatabaseBinding

class LatihanRoomDatabase : AppCompatActivity() {

    private val binding: ActivityLatihanRoomDatabaseBinding by lazy { ActivityLatihanRoomDatabaseBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}

data class User(
    val id: Int,
    val username: String,
    val email: String
)