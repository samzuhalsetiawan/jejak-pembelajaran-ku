package com.example.learnfundamental

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup

class LatihanRadioButton : AppCompatActivity() {

    private lateinit var btnOrder: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latihan_radio_button)

        btnOrder = findViewById(R.id.btnOrder)
        btnOrder.setOnClickListener {
            val rgPilihanDaging = findViewById<RadioGroup>(R.id.rgPilihanDaging)
            val pilihanDaging = when(rgPilihanDaging.checkedRadioButtonId) {
                R.id.rbDagingAyam -> "Ayam"
                R.id.rbDagingKambing -> "Kambing"
                R.id.rbDagingSapi -> "Sapi"
                else -> "Tidak Ada Pilihan"
            }
            val tambahanTopping = mutableListOf<String>()
            if (findViewById<CheckBox>(R.id.cbToppingSelada).isChecked) tambahanTopping.add("Selada")
            if (findViewById<CheckBox>(R.id.cbToppingKeju).isChecked) tambahanTopping.add("Keju")
            if (findViewById<CheckBox>(R.id.cbToppingTelur).isChecked) tambahanTopping.add("Telur")

            Log.d("LatihanRadioButton", tambahanTopping.toString())
            val toOrderanBurger = Intent(this, OrderanBurger::class.java)
            toOrderanBurger.putExtra("EXTRA_BURGER", Burger(pilihanDaging, tambahanTopping))
            startActivity(toOrderanBurger)
        }

    }
}