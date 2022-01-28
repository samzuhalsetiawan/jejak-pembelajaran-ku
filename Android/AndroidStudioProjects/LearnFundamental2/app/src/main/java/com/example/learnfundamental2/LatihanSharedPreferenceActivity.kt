package com.example.learnfundamental2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.learnfundamental2.databinding.ActivityLatihanSharedPreferenceBinding

class LatihanSharedPreferenceActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLatihanSharedPreferenceBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val myPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)

        binding.btnSave.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString().toInt()
            val isAdult = binding.cbIsAdult.isChecked
            myPref.edit().apply {
                putString("username", username)
                putInt("password", password)
                putBoolean("isAdult", isAdult)
                apply()
            }
        }

        binding.btnLoad.setOnClickListener {
            binding.etUsername.setText(myPref.getString("username", "No Name"))
            binding.etPassword.setText(myPref.getInt("password", 0).toString())
            binding.cbIsAdult.isChecked = myPref.getBoolean("isAdult", false)
        }

    }
}