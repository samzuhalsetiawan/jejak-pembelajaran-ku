package com.samzuhalsetiawan.kloningwhatsapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.samzuhalsetiawan.kloningwhatsapp.databinding.ActivityMainPageBinding
import com.samzuhalsetiawan.kloningwhatsapp.utils.UserPhoneContacts

class MainPageActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainPageBinding.inflate(layoutInflater) }
    private val auth by lazy { Firebase.auth }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLogOut.setOnClickListener {
            auth.signOut()
            Intent(this, LoginActivity::class.java).also {
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(it)
            }
            finish()
        }

        UserPhoneContacts.getReadAccessToUserContacts(this) { isGranted ->
            when {
                isGranted -> {
                    binding.btnFindUser.setOnClickListener {
                        startActivity(Intent(this, FindUserActivity::class.java))
                    }
                }
            }
        }

    }
}