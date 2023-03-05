package com.example.latihannavcomp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.latihannavcomp.databinding.ActivityPalingPertamaBinding

class ActivityPalingPertama : AppCompatActivity() {

    private lateinit var binding: ActivityPalingPertamaBinding

    companion object {
        const val SHARED_PREF_NAME = "com.example.latihannavcomp.onBoarding"
        const val FINNISH_ONBOARDING_KEY = "com.example.latihannavcomp.isFinnishOnBoarding"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPalingPertamaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        Handler().postDelayed({
            if (isFinnishOnBoarding()) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            } else {
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.navContainerView) as NavHostFragment
                val navController = navHostFragment.findNavController()
                val action = SplashScreenFragmentDirections.actionSplashScreenFragmentToViewPagerFragment()
                navController.navigate(action)
            }
        }, 5000)
    }

    private fun isFinnishOnBoarding(): Boolean {
        val sharedPref = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(FINNISH_ONBOARDING_KEY, false)
    }
}