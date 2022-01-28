package com.example.learnfundamental2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.learnfundamental2.databinding.ActivityLatihanNavigationDrawerBinding

class LatihanNavigationDrawerActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLatihanNavigationDrawerBinding.inflate(layoutInflater) }

    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        drawerToggle = ActionBarDrawerToggle(this, binding.dlLatihanDrawer, R.string.open, R.string.close)
        binding.dlLatihanDrawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.nvDrawer.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.itDrawerItem1 -> Toast.makeText(this, "Item 1", Toast.LENGTH_SHORT).show()
                R.id.itDrawerItem2 -> Toast.makeText(this, "Item 2", Toast.LENGTH_SHORT).show()
                R.id.itDrawerItem3 -> Toast.makeText(this, "Item 3", Toast.LENGTH_SHORT).show()
            }
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

}