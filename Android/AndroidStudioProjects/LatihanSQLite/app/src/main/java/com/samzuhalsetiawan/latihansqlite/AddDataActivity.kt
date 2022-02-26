package com.samzuhalsetiawan.latihansqlite

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.samzuhalsetiawan.latihansqlite.database.MyDatabase
import com.samzuhalsetiawan.latihansqlite.databinding.ActivityAddDataBinding
import com.samzuhalsetiawan.latihansqlite.model.User

class AddDataActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddDataBinding.inflate(layoutInflater) }
    companion object {
        lateinit var myUserDatabase: MyDatabase
    }
    private val myDatabase by lazy {
        MyDatabase(this).also {
            myUserDatabase = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnAddData.setOnClickListener { addDataToDatabase() }

        binding.btnBatal.setOnClickListener { kembaliKeHalamanHome() }

    }

    private fun addDataToDatabase() {
        val name = binding.etPersonName.text.toString()
        val age = binding.etPersonAge.text.toString().toInt()
        val user = User(name, age)
        when {
            myDatabase.addOne(user) -> Toast.makeText(this, "Success to add data", Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(this, "Failed to add data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun kembaliKeHalamanHome() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
    }
}