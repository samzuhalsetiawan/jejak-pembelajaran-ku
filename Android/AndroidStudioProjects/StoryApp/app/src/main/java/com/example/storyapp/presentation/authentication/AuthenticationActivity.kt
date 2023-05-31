package com.example.storyapp.presentation.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityAuthenticationBinding
import com.example.storyapp.domain.sealed_class.AuthenticationActivityEvent
import com.example.storyapp.domain.utils.viewBindings
import com.example.storyapp.presentation.dialogs.DialogSimpleWarning
import com.example.storyapp.presentation.main.MainActivity

class AuthenticationActivity : AppCompatActivity(R.layout.activity_authentication) {

    private val binding by viewBindings(ActivityAuthenticationBinding::bind)
    private val authenticationViewModel: AuthenticationViewModel by viewModels()
    private var shouldKeepSplashScreenOnScreen: Boolean = true

    private val simpleDialog by lazy { DialogSimpleWarning() }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { shouldKeepSplashScreenOnScreen }
        super.onCreate(savedInstanceState)

        authenticationViewModel.events.observe(this) {  event ->
            when (event) {
                is AuthenticationActivityEvent.OnError -> showErrorDialog(event.error)
                is AuthenticationActivityEvent.RedirectUserToMain -> redirectUserToMain()
                is AuthenticationActivityEvent.ShowLoading -> showLoading()
                is AuthenticationActivityEvent.UserNotLoginYet -> Unit
                is AuthenticationActivityEvent.RegisterSuccess -> onRegisterSuccess(event.email)
            }
            when {
                event !is AuthenticationActivityEvent.ShowLoading &&
                event !is AuthenticationActivityEvent.RedirectUserToMain -> closeLoading().also { shouldKeepSplashScreenOnScreen = false }
            }
        }
    }

    private fun onRegisterSuccess(email: String) {
        val navHost = supportFragmentManager.findFragmentById(R.id.fcvAuthNav) as NavHostFragment
        val navController = navHost.navController
        val bundle = Bundle().apply { putString(EXTRA_EMAIL, email) }
        navController.navigate(R.id.loginFragment, bundle)
    }

    private fun showLoading() {
        binding.fcvAuthNav.visibility = View.GONE
        binding.pbAuthActivity.visibility = View.VISIBLE
    }

    private fun closeLoading() {
        binding.fcvAuthNav.visibility = View.VISIBLE
        binding.pbAuthActivity.visibility = View.GONE
    }

    private fun redirectUserToMain() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK + Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun showErrorDialog(error: Throwable) {
        simpleDialog.setMessage(error.message.toString())
        simpleDialog.show(supportFragmentManager, "simple-dialog")
    }

    companion object {
        const val EXTRA_EMAIL = "com.example.storyapp.EXTRA_EMAIL"
    }
}