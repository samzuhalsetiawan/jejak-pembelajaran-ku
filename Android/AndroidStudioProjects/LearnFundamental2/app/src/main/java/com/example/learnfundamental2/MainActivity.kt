package com.example.learnfundamental2

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.learnfundamental2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToLatihanImplicitIntent.setOnClickListener {
            startActivity(Intent(this, LatihanImplicitIntentActivity::class.java))
        }
        binding.btnToLatihanImplicitIntent2.setOnClickListener {
            startActivity(Intent(this, LatihanImplicitIntent2Activity::class.java))
        }
        binding.btnToLatihanImplicitIntent3.setOnClickListener {
            startActivity(Intent(this, LatihanImplicitIntent3Activity::class.java))
        }
        binding.btnToLatihanMenuItem.setOnClickListener {
            startActivity(Intent(this, LatihanMenuItemActivity::class.java))
        }
        binding.btnToLatihanAlertDialog.setOnClickListener {
            startActivity(Intent(this, LatihanAlertDialogActivity::class.java))
        }
        binding.btnToLatihanSpinner.setOnClickListener {
            startActivity(Intent(this, LatihanSpinnerActivity::class.java))
        }
        binding.btnToLatihanRecyclerView.setOnClickListener {
            startActivity(Intent(this, LatihanRecyclerViewActivity::class.java))
        }
        binding.btnToLatihanFragment.setOnClickListener {
            startActivity(Intent(this, LatihanFragmentActivity::class.java))
        }
        binding.btnToLatihanBottomNavigation.setOnClickListener {
            startActivity(Intent(this, LatihanBottomNavigationActivity::class.java))
        }
        binding.btnToLatihanViewPager2.setOnClickListener {
            startActivity(Intent(this, LatihanViewPager2::class.java))
        }
        binding.btnToLatihanTabLayout.setOnClickListener {
            startActivity(Intent(this, LatihanTabLayoutActivity::class.java))
        }
        binding.btnToLatihanNavigationDrawer.setOnClickListener {
            startActivity(Intent(this, LatihanNavigationDrawerActivity::class.java))
        }
        binding.btnToLatihanSharedPreference.setOnClickListener {
            startActivity(Intent(this, LatihanSharedPreferenceActivity::class.java))
        }
        binding.btnToLatihanNotofication.setOnClickListener {
            startActivity(Intent(this, LatihanNotificationActivity::class.java))
        }
        binding.btnToLatihanIntentService.setOnClickListener {
            startActivity(Intent(this, LatihanIntentServiceActivity::class.java))
        }
        binding.btnToLatihanIntentService2.setOnClickListener {
            startActivity(Intent(this, LatihanIntentService2Activity::class.java))
        }
        binding.btnToLatihanService.setOnClickListener {
            startActivity(Intent(this, LatihanServiceActivity::class.java))
        }
        binding.btnToLatihanDragAndDrop.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                startActivity(Intent(this, LatihanDragAndDropActivity::class.java))
            } else {
                Toast.makeText(this, "Maaf Device Anda Tidak Support Fitur Ini", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnToLatihanBroadcast.setOnClickListener {
            startActivity(Intent(this, LatihanBroadcastActivity::class.java))
        }
    }
}