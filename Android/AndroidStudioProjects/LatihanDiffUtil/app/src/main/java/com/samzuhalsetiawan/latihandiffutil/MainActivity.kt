package com.samzuhalsetiawan.latihandiffutil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.samzuhalsetiawan.latihandiffutil.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val personAdapter by lazy { PersonAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeAdapterVH()
        personAdapter.submitList(PersonData.listOfPerson)
        var currentAddCount = 0
        binding.btnAddPerson.setOnClickListener {
            PersonData.listOfPerson.add(PersonData.newListOfPerson[currentAddCount])
            personAdapter.submitList(PersonData.listOfPerson)
            currentAddCount++
        }
    }

    private fun initializeAdapterVH() {
        binding.rvListNama.apply {
            adapter = personAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

}