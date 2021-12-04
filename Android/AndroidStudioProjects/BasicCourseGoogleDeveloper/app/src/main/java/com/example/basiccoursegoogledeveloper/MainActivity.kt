package com.example.basiccoursegoogledeveloper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.basiccoursegoogledeveloper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val angka: Double = 1.0

        /*
            this is multiline comment
         */

        val tes = 1.2..6.3

    }
}