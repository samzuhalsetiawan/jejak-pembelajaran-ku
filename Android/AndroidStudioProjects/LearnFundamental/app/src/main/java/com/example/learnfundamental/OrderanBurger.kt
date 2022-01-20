package com.example.learnfundamental

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class OrderanBurger : AppCompatActivity() {

    private lateinit var tvRincianPesanan: TextView
    private lateinit var btnOrderAgain: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orderan_burger)

        val dataPesanan = intent.getSerializableExtra("EXTRA_BURGER") as Burger

        tvRincianPesanan = findViewById(R.id.tvRincianPesanan)
        btnOrderAgain = findViewById(R.id.btnOrderAgain)
        val rincian = StringBuilder()
        rincian.append("Anda memesan burger dengan daging ${dataPesanan.daging}. ")
        when {
            dataPesanan.toppings.isNotEmpty() -> {
                rincian.append("Dengan tambahan topping ")
                for ((index, topping) in dataPesanan.toppings.withIndex()) {
                    when {
                        index != 0 -> {
                            when {
                                dataPesanan.toppings.size != 2 -> rincian.append(", ")
                                else -> rincian.append(" ")
                            }
                        }
                    }
                    if (index == dataPesanan.toppings.lastIndex) {
                        if (index != 0) rincian.append("dan ")
                        rincian.append("$topping.")
                    } else {
                        rincian.append(topping)
                    }
                }
            }
        }
        tvRincianPesanan.text = rincian.toString()

        btnOrderAgain.setOnClickListener {
            startActivity(Intent(this, LatihanRadioButton::class.java))
        }

    }
}