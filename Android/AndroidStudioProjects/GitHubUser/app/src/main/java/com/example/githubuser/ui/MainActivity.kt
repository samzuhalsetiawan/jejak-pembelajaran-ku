package com.example.githubuser.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.MenuProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.ui.viewmodel.GitHubUserViewModel
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener

class MainActivity : AppCompatActivity(), MenuProvider, OnNavigationItemSelectedListener {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val gitHubUserViewModel: GitHubUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(binding.root)

        setupAppThemeMode()

        addMenuProvider(this)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.searchFragment, R.id.detailFragment, R.id.favoriteUserFragment), binding.mainDrawerLayout
        )
        supportActionBar?.elevation = 0f
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.nvDrawer.apply {
            setupWithNavController(navController)
            setCheckedItem(R.id.menuSearchUser)
            setNavigationItemSelectedListener(this@MainActivity)
        }
    }

    private fun setupAppThemeMode() {
        gitHubUserViewModel.darkThemePreference.observe(this) { isEnabled ->
            AppCompatDelegate.setDefaultNightMode(
                if (isEnabled) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menuAboutMe -> true.also {
                navController.popBackStack(R.id.aboutMeFragment, true)
                navController.navigate(R.id.aboutMeFragment)
            }
            R.id.menuDarkMode -> true.also {
                menuItem.isChecked = !menuItem.isChecked
                gitHubUserViewModel.setDarkModeEnabled(menuItem.isChecked)
            }
            else -> false
        }
    }

    override fun onPrepareMenu(menu: Menu) {
        val switchDarkMode = menu.findItem(R.id.menuDarkMode)
        switchDarkMode.isChecked = gitHubUserViewModel.darkThemePreference.value ?: false
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuSearchUser -> true.also {
                navController.popBackStack(R.id.searchFragment, true)
                navController.navigate(R.id.searchFragment)
            }
            R.id.menuFavoriteUser -> true.also {
                navController.popBackStack(R.id.favoriteUserFragment, true)
                navController.navigate(R.id.favoriteUserFragment)
            }
            else -> false
        }
    }
}