package com.example.colormixer

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.colormixer.databinding.ActivityMainBinding
import com.google.android.material.card.MaterialCardView
import com.google.android.material.slider.Slider
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    companion object {
        private lateinit var binding: ActivityMainBinding
        private lateinit var activeCard: MaterialCardView
        var activeColor by Delegates.notNull<Int>()
        lateinit var slKomposisi: Slider

        private fun onColorChange(warnaBaru: Int) {
            when (activeCard) {
                binding.cvWarna1 -> CardColor.warna1 = warnaBaru
                binding.cvWarna2 -> CardColor.warna2 = warnaBaru
            }
            campurWarna()
            activeCard.setCardBackgroundColor(warnaBaru)
            activeColor = warnaBaru
        }

        fun setSliderTo(color: Int) {
            binding.slMerah.value = color.red / 255f
            binding.slHijau.value = color.green / 255f
            binding.slBiru.value = color.blue / 255f
        }

        private fun campurWarna() {
            val rasio = 1 - binding.slKomposisi.value
            val newRed =
                getKomposisi(CardColor.warna1.red / 255f, CardColor.warna2.red / 255f, rasio)
            val newGreen =
                getKomposisi(CardColor.warna1.green / 255f, CardColor.warna2.green / 255f, rasio)
            val newBlue =
                getKomposisi(CardColor.warna1.blue / 255f, CardColor.warna2.blue / 255f, rasio)
            CardColor.warna3 = Color.rgb(newRed, newGreen, newBlue)
            binding.cvWarna3.setCardBackgroundColor(CardColor.warna3)
            updateColorLabelKomposisi()
            updateColorLabel()
        }

        private fun updateColorLabelKomposisi() {
            binding.cvKomposisiLabelWarna1.setCardBackgroundColor(CardColor.warna1)
            binding.cvKomposisiLabelWarna2.setCardBackgroundColor(CardColor.warna2)
        }

        private fun updateColorLabel() {
            val warna1 =
                "#${intToHexColor(CardColor.warna1.red)}${intToHexColor(CardColor.warna1.green)}${
                    intToHexColor(CardColor.warna1.blue)
                }"
            val warna2 =
                "#${intToHexColor(CardColor.warna2.red)}${intToHexColor(CardColor.warna2.green)}${
                    intToHexColor(CardColor.warna2.blue)
                }"
            val warna3 =
                "#${intToHexColor(CardColor.warna3.red)}${intToHexColor(CardColor.warna3.green)}${
                    intToHexColor(CardColor.warna3.blue)
                }"
            binding.tvLabelCvWarna1.text = warna1
            binding.tvLabelCvWarna2.text = warna2
            binding.tvLabelCvWarna3.text = warna3
        }

        private fun getKomposisi(color1: Float, color2: Float, rasio: Float): Float {
            val selisih = when {
                color1 < color2 -> color2 - color1
                else -> color1 - color2
            }
            return when {
                color1 < color2 -> color2 - selisih * rasio
                color1 > color2 -> color2 + selisih * rasio
                else -> color1
            }
        }

        private fun intToHexColor(color: Int): String {
            return when (val hexString = Integer.toHexString(color)) {
                "0" -> "00"
                else -> hexString
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activeCard = binding.cvWarna1
        activeColor = CardColor.warna1
        slKomposisi = binding.slKomposisi

        recyclerView = binding.rvColorTemplate
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = ColorListAdapter()

        campurWarna()

        binding.apply {
            cvWarna1.setCardBackgroundColor(CardColor.warna1)
            cvWarna2.setCardBackgroundColor(CardColor.warna2)
            cvWarna3.setCardBackgroundColor(CardColor.warna3)
            cvWarna1.setOnClickListener {
                activeColor = CardColor.warna1
                activeCard = cvWarna1
                setSliderTo(activeColor)
                slKomposisi.value = 0.5f
            }
            cvWarna2.setOnClickListener {
                activeColor = CardColor.warna2
                activeCard = cvWarna2
                setSliderTo(activeColor)
                slKomposisi.value = 0.5f
            }
            slMerah.addOnChangeListener { _, value, _ ->
                val warnaBaru = Color.rgb(value, slHijau.value, slBiru.value)
                onColorChange(warnaBaru)
            }
            slHijau.addOnChangeListener { _, value, _ ->
                val warnaBaru = Color.rgb(slMerah.value, value, slBiru.value)
                onColorChange(warnaBaru)
            }
            slBiru.addOnChangeListener { _, value, _ ->
                val warnaBaru = Color.rgb(slMerah.value, slHijau.value, value)
                onColorChange(warnaBaru)
            }
            slKomposisi.addOnChangeListener { _, _, _ ->
                val warnaBaru = Color.rgb(slMerah.value, slHijau.value, slBiru.value)
                onColorChange(warnaBaru)
            }
        }
    }
}