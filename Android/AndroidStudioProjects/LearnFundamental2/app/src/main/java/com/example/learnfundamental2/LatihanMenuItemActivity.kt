package com.example.learnfundamental2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.learnfundamental2.databinding.ActivityLatihanMenuItemBinding

class LatihanMenuItemActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLatihanMenuItemBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.latihan_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.miAddContact -> Toast.makeText(this, "You Press Item Add Contact", Toast.LENGTH_SHORT).show()
            R.id.miFavorite -> Toast.makeText(this, "You Press Item Favorite", Toast.LENGTH_SHORT).show()
            R.id.miSettings -> Toast.makeText(this, "You Press Item Settings", Toast.LENGTH_SHORT).show()
            R.id.miAboutme -> Toast.makeText(this, "You Press Item About Me", Toast.LENGTH_SHORT).show()
            R.id.miCloseApp -> finish()
        }
        return true
    }

}