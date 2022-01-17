package com.example.colormixer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.colormixer.databinding.ColorTemplateCardBinding

class ColorListAdapter : RecyclerView.Adapter<ColorListAdapter.ColorListHolder>() {

    class ColorListHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ColorTemplateCardBinding.bind(view)
        val colorCard = binding.cvTemplateColor
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorListHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.color_template_card, parent, false)
        return ColorListHolder(view)
    }

    override fun onBindViewHolder(holder: ColorListHolder, position: Int) {
        holder.colorCard.setCardBackgroundColor(CardColor.templateWarna[position])
        holder.colorCard.setOnClickListener {
            when (MainActivity.activeColor) {
                CardColor.warna1 -> {
                    CardColor.warna1 = CardColor.templateWarna[position]
                    MainActivity.setSliderTo(CardColor.warna1)
                }
                CardColor.warna2 -> {
                    CardColor.warna2 = CardColor.templateWarna[position]
                    MainActivity.setSliderTo(CardColor.warna2)
                }
            }
            MainActivity.slKomposisi.value = 0.5f
        }
    }

    override fun getItemCount(): Int = CardColor.templateWarna.size
}