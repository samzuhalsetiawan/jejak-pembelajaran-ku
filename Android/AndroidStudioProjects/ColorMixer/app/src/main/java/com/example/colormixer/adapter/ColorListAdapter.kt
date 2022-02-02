package com.example.colormixer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.recyclerview.widget.RecyclerView
import com.example.colormixer.MainActivity
import com.example.colormixer.R
import com.example.colormixer.data.CardColor
import com.example.colormixer.data.SliderColor
import com.example.colormixer.data.Warna
import com.example.colormixer.databinding.ColorTemplateCardBinding

class ColorListAdapter : RecyclerView.Adapter<ColorListAdapter.ColorListHolder>() {

    inner class ColorListHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ColorTemplateCardBinding.bind(view)
        val colorCard = binding.cvTemplateColor
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorListHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.color_template_card, parent, false)
        return ColorListHolder(view)
    }

    override fun onBindViewHolder(holder: ColorListHolder, position: Int) {
        val color = CardColor.templateWarna[position]
        val colorR = color.red
        val colorG = color.green
        val colorB = color.blue
        fun updateWarna(warna: Warna) {
            warna.merah = colorR / 255f
            warna.hijau = colorG / 255f
            warna.biru = colorB / 255f
        }

        fun updateSlider(position: Int) {
            val sliderViewHolder =
                SliderViewPagerAdapter.viewHolders.find { it.layoutPosition == position }
            sliderViewHolder?.slMerah?.value = colorR / 255f
            sliderViewHolder?.slHijau?.value = colorG / 255f
            sliderViewHolder?.slBiru?.value = colorB / 255f
        }
        holder.colorCard.setCardBackgroundColor(color)
        holder.colorCard.setOnClickListener {
            when (MainActivity.activeTab) {
                MainActivity.TAB_PERTAMA -> updateWarna(SliderColor.warna1)
                MainActivity.TAB_KEDUA -> updateWarna(SliderColor.warna2)
            }
            updateSlider(MainActivity.activeTab)
        }
    }

    override fun getItemCount(): Int = CardColor.templateWarna.size
}