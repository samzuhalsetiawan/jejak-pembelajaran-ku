package com.samzuhalsetiawan.projek

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.samzuhalsetiawan.projek.databinding.ActivityMainHomeBinding

class MainHome : AppCompatActivity() {
    private val binding by lazy { ActivityMainHomeBinding.inflate(layoutInflater) }

    private val homeFragment = HomeFragment()
    private val settingFragment = SettingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        supportFragmentManager.beginTransaction().apply {
            replace(binding.fcvMainContainer.id, homeFragment)
            commit()
        }

        binding.bnMainNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuHome -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(binding.fcvMainContainer.id, homeFragment)
                        commit()
                    }
                    true
                }
                R.id.menuOrder -> {
                    true
                }
                R.id.menuSetting -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(binding.fcvMainContainer.id, settingFragment)
                        commit()
                    }
                    true
                }
                else -> false
            }
        }

    }

    private fun getDataOjol(key: String): String? {
        val sharePref = getSharedPreferences("Projek", Context.MODE_PRIVATE)
        return sharePref.getString(key, null)
    }

}