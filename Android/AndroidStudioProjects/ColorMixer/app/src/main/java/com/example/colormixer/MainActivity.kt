package com.example.colormixer

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.colormixer.adapter.ColorListAdapter
import com.example.colormixer.adapter.SliderViewPagerAdapter
import com.example.colormixer.data.SliderColor
import com.example.colormixer.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    companion object {
        private lateinit var binding: ActivityMainBinding
        const val TAB_PERTAMA = 0
        const val TAB_KEDUA = 1
        var activeTab = TAB_PERTAMA

        fun updateColor() {
            val warna1 = Color.rgb(
                SliderColor.warna1.merah,
                SliderColor.warna1.hijau,
                SliderColor.warna1.biru
            )
            val warna2 = Color.rgb(
                SliderColor.warna2.merah,
                SliderColor.warna2.hijau,
                SliderColor.warna2.biru
            )
            binding.viewMainWarna1.setBackgroundColor(warna1)
            binding.viewMainWarna2.setBackgroundColor(warna2)
            campurWarna(warna1, warna2)
        }

        private fun campurWarna(warna1: Int, warna2: Int) {
            val rasio = binding.sliderMainKomposisi.value
            val newRed =
                getKomposisi(warna1.red / 255f, warna2.red / 255f, rasio)
            val newGreen =
                getKomposisi(warna1.green / 255f, warna2.green / 255f, rasio)
            val newBlue =
                getKomposisi(warna1.blue / 255f, warna2.blue / 255f, rasio)
            val warnaHasil = Color.rgb(newRed, newGreen, newBlue)
            binding.viewMainWarnaHasil.setBackgroundColor(warnaHasil)
            updateColorLabel(warna1, warna2, warnaHasil)
        }

        private fun updateColorLabel(warna1: Int, warna2: Int, warnaHasil: Int) {
            val hexWarna1 =
                "#${intToHexColor(warna1.red)}${intToHexColor(warna1.green)}${
                    intToHexColor(warna1.blue)
                }"
            val hexWarna2 =
                "#${intToHexColor(warna2.red)}${intToHexColor(warna2.green)}${
                    intToHexColor(warna2.blue)
                }"
            val hexWarnaHasil =
                "#${intToHexColor(warnaHasil.red)}${intToHexColor(warnaHasil.green)}${
                    intToHexColor(warnaHasil.blue)
                }"
            binding.tvMainLabelWarna1.text = hexWarna1
            binding.tvMainLabelWarna2.text = hexWarna2
            binding.tvMainLabelWarnaHasil.text = hexWarnaHasil
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

        recyclerView = binding.rvColorTemplate
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = ColorListAdapter()

        binding.vpMainSliderViewPager.adapter =
            SliderViewPagerAdapter(listOf(SliderColor.warna1, SliderColor.warna2))

        TabLayoutMediator(
            binding.tlMainSliderTab,
            binding.vpMainSliderViewPager
        ) { tab: TabLayout.Tab, i: Int ->
            when (i) {
                0 -> tab.text = "Warna Pertama"
                1 -> tab.text = "Warna Kedua"
            }
        }.attach()

        binding.tlMainSliderTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    when (it.position) {
                        0 -> activeTab = TAB_PERTAMA
                        1 -> activeTab = TAB_KEDUA
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })

        binding.sliderMainKomposisi.addOnChangeListener { _, value, _ ->
            updateColor()
            updatePercentages(value)
        }

        updateColor()

    }

    private fun updatePercentages(value: Float) {
        val percent = (value * 100).toInt()
        binding.tvMainKompisisiWarna1.text = resources.getString(R.string.percent).format(percent)
        binding.tvMainKompisisiWarna2.text =
            resources.getString(R.string.percent).format(100 - percent)
    }

}