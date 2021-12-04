package com.example.basiccoursegoogledeveloper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.basiccoursegoogledeveloper.databinding.ActivityTipBinding
import java.text.NumberFormat

class TipActivity : AppCompatActivity() {

    lateinit var binding: ActivityTipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val formatedCurrency = NumberFormat.getCurrencyInstance().format(1_500_000)
        binding.tvFormatedCurrency.text = getString(R.string.curency, formatedCurrency)

        binding.btnDebug1.setOnClickListener {
            Toast.makeText(this,
                when(binding.rg1.checkedRadioButtonId)
                {
                    R.id.rb_option_1 -> "Option 1"
                    R.id.rb_option_2 -> "Option 2"
                    R.id.rb_option_3 -> "Option 3"
                    else -> "None"
                },
                Toast.LENGTH_SHORT).show()
        }
        binding.btnDebug2.setOnClickListener {
            Toast.makeText(this, binding.scSwitch1.isChecked.toString(), Toast.LENGTH_SHORT).show()
        }
        println(binding)
    }
}