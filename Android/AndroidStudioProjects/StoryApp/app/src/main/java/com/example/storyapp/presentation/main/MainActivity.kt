package com.example.storyapp.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.domain.sealed_class.MainActivityEvent
import com.example.storyapp.domain.utils.viewBindings
import com.example.storyapp.presentation.authentication.AuthenticationActivity
import com.example.storyapp.presentation.dialogs.DialogSimpleWarning

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBindings(ActivityMainBinding::bind)
    private val mainViewModel: MainViewModel by viewModels()

    private val simpleDialog by lazy { DialogSimpleWarning() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navController = (supportFragmentManager.findFragmentById(R.id.fcvMainNav) as NavHostFragment).navController
        binding.bnvMainBottomNav.setupWithNavController(navController)

        mainViewModel.event.observe(this) { event ->
            when (event) {
                is MainActivityEvent.OnData -> closeLoading()
                is MainActivityEvent.OnError -> showError(event.displayErrorMessage).also { closeLoading() }
                is MainActivityEvent.OnLoading -> showLoading()
                is MainActivityEvent.OnLogout -> onLogout()
                is MainActivityEvent.OnPostingSuccess -> onPostingSuccess()
            }
        }
    }

    private fun onPostingSuccess() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fcvMainNav) as NavHostFragment
        val navController = navHost.navController
        closeLoading()
        navController.navigate(R.id.action_createStoryFragment_to_homeFragment)
    }

    private fun onLogout() {
        val intent = Intent(this, AuthenticationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK + Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun closeLoading() {
        binding.fcvMainNav.visibility = View.VISIBLE
        binding.pbMainActivity.visibility = View.GONE
    }

    private fun showError(displayErrorMessage: String) {
        simpleDialog.setMessage(displayErrorMessage)
        simpleDialog.show(supportFragmentManager, null)
    }

    private fun showLoading() {
        binding.fcvMainNav.visibility = View.GONE
        binding.pbMainActivity.visibility = View.VISIBLE
    }

}