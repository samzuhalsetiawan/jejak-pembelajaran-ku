package com.samzuhalsetiawan.latihanmvvm2.ui.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.samzuhalsetiawan.latihanmvvm2.R
import com.samzuhalsetiawan.latihanmvvm2.data.ShoppingAdapter
import com.samzuhalsetiawan.latihanmvvm2.data.db.ShoppingDatabase
import com.samzuhalsetiawan.latihanmvvm2.data.db.entities.ShoppingItem
import com.samzuhalsetiawan.latihanmvvm2.data.repositories.ShoppingRepository
import com.samzuhalsetiawan.latihanmvvm2.databinding.ActivityShoppingBinding
import com.samzuhalsetiawan.latihanmvvm2.databinding.AlertAddItemBinding

//Tahap tahap membuat room
//1. implement dependencies
//2. buat data class sebagai model untuk entites (column), need primary key
//3. buat interface Dao, berisi semua function untuk mengakses database
//4. buat abstract class database, berisi 1 method abstract yang mereturn dao, jadikan singleton

class ShoppingActivity : AppCompatActivity() {

    private val binding by lazy { ActivityShoppingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val database = ShoppingDatabase(this)
        val repository = ShoppingRepository(database)
        val shoppingViewModelFactory = ShoppingViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, shoppingViewModelFactory).get(ShoppingViewModel::class.java)

        val adapter = ShoppingAdapter(listOf(), viewModel)
        val layoutManager = LinearLayoutManager(this)
        binding.rvShopping.let {
            it.layoutManager = layoutManager
            it.adapter = adapter
        }

        viewModel.getAllShoppingItems().observe(this) {
            adapter.shoppingList = it
            adapter.notifyDataSetChanged()
        }
        val alertDialog = AlertDialog.Builder(this)
            .setView(R.layout.alert_add_item)
            .create().apply {
                val alertViewBinding = AlertAddItemBinding.inflate(layoutInflater)
                alertViewBinding.tvAdd.setOnClickListener {
                    val name = alertViewBinding.etItemName.text
                    val amount = alertViewBinding.etItemAmount.text
                    if (name.isNotBlank() and amount.isNotBlank()) {
                        val shoppingItem = ShoppingItem(name.toString(), amount.toString().toInt())
                        viewModel.upsert(shoppingItem)
                        dismiss()
                    } else {
                        Toast.makeText(context,"Please fill the input", Toast.LENGTH_SHORT).show()
                    }
                }
                alertViewBinding.tvCancel.setOnClickListener { cancel() }
            }
        binding.fabPlus.setOnClickListener {
            alertDialog.show()
        }
    }
}