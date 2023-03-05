package com.example.latihannavcomp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.latihannavcomp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var appBarConfiguration2: AppBarConfiguration
    private lateinit var navigationListener: NavController.OnDestinationChangedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        navController = findNavController(R.id.fragmentContainerView)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        binding.navigationView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.root)

        appBarConfiguration2 = AppBarConfiguration(setOf(R.id.FirstFragment, R.id.thirdFragment, R.id.fourthFragment))

        navigationListener = NavController.OnDestinationChangedListener { _: NavController, navDestination: NavDestination, _: Bundle? ->
            when (navDestination.id) {
                R.id.FirstFragment -> Snackbar.make(binding.root, "First Fragment", Snackbar.LENGTH_SHORT).show()
                R.id.SecondFragment -> Snackbar.make(binding.root, "Second Fragment", Snackbar.LENGTH_SHORT).show()
                R.id.thirdFragment -> Snackbar.make(binding.root, "Third Fragment", Snackbar.LENGTH_SHORT).show()
                R.id.fourthFragment -> Snackbar.make(binding.root, "Fourth Fragment", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.bottomNav.setupWithNavController(navController)

        setupActionBarWithNavController(navController, appBarConfiguration)
//        setupActionBarWithNavController(navController, appBarConfiguration2)
    }

    override fun onResume() {
        navController.addOnDestinationChangedListener(navigationListener)
        super.onResume()
    }

    override fun onPause() {
        navController.removeOnDestinationChangedListener(navigationListener)
        super.onPause()
    }

    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
        return navController.navigateUp(binding.root) || super.onSupportNavigateUp()
    }
}