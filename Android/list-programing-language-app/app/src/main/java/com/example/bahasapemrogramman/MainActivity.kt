package com.example.bahasapemrogramman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvMain: RecyclerView
    private lateinit var listPL: ArrayList<ProgrammingLanguage>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listPL = initPL()
        rvMain = findViewById(R.id.rv_main)
        rvMain.layoutManager = LinearLayoutManager(this)
        val mainListAdapter = MainListCardAdapter(listPL)
        rvMain.adapter = mainListAdapter
        mainListAdapter.setOnItemSelected(object : MainListCardAdapter.OnItemSelected {
            override fun itemSelected(position: Int, name: String) {
                val intentToDetailActivity = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetailActivity.putExtra(DetailActivity.EXTRA_POSITION, position)
                intentToDetailActivity.putExtra(DetailActivity.EXTRA_NAME, name)
                startActivity(intentToDetailActivity)
            }
        })

    }

    private fun initPL(): ArrayList<ProgrammingLanguage> {
        val listNama = resources.getStringArray(R.array.programming_language)
        val listShortDetail = resources.getStringArray(R.array.short_detail)
        val listLogo = PLDetails.logo
        val myList = arrayListOf<ProgrammingLanguage>()
        for (posision in listNama.indices) {
            myList.add(ProgrammingLanguage(listNama[posision], listShortDetail[posision], listLogo[posision]))
        }
        return myList
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_about -> {
                val intentToAboutMe = Intent(this, AboutMe::class.java)
                startActivity(intentToAboutMe)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}