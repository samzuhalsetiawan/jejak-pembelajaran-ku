package com.example.learnfundamental2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.learnfundamental2.databinding.ActivityLatihanBottomNavigationBinding
import com.example.learnfundamental2.dummyfragment.FirstFragment
import com.example.learnfundamental2.dummyfragment.SecondFragment
import com.example.learnfundamental2.dummyfragment.ThirdFragment

class LatihanBottomNavigationActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLatihanBottomNavigationBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val firstFragment = FirstFragment()
        val secondFragment = SecondFragment()
        val thirdFragment = ThirdFragment()

        changeFragment(firstFragment, false)

        binding.bnBottomNavigation.getOrCreateBadge(R.id.itMessage).apply {
            number = 999
        }

        binding.bnBottomNavigation.setOnItemSelectedListener { menu: MenuItem ->
            when (menu.itemId) {
                R.id.itHome -> {
                    changeFragment(firstFragment)
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                }
                R.id.itMessage -> {
                    changeFragment(secondFragment)
                    Toast.makeText(this, "Message", Toast.LENGTH_SHORT).show()
                }
                R.id.itPerson -> {
                    changeFragment(thirdFragment)
                    Toast.makeText(this, "Person", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

    }

    private fun changeFragment(fragment: Fragment, addToStack: Boolean = true) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragmentContainer, fragment)
            if (addToStack) addToBackStack(null)
            commit()
        }
    }

}