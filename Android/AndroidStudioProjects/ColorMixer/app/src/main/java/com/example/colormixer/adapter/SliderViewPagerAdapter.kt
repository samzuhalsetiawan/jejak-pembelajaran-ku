package com.example.colormixer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.colormixer.MainActivity
import com.example.colormixer.R
import com.example.colormixer.data.SliderColor
import com.example.colormixer.data.Warna
import com.example.colormixer.databinding.SliderViewpagerLayoutBinding
import com.google.android.material.slider.Slider

class SliderViewPagerAdapter(private val listWarna: List<Warna>) :
    RecyclerView.Adapter<SliderViewPagerAdapter.ViewHolder>() {
    companion object {
        val viewHolders = mutableSetOf<ViewHolder>()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = SliderViewpagerLayoutBinding.bind(view)
        val slMerah = binding.slVpSliderMerah
        val slHijau = binding.slVpSliderHijau
        val slBiru = binding.slVpSliderBiru
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.slider_viewpager_layout, parent, false)
        return ViewHolder(view).also { viewHolders.add(it) }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        fun updateSliderData(slider: Slider, value: Float, warna: Warna) {
            when (slider) {
                holder.slMerah -> warna.merah = value
                holder.slHijau -> warna.hijau = value
                holder.slBiru -> warna.biru = value
            }
        }

        val listener = { slider: Slider, value: Float, _: Boolean ->
            when (MainActivity.activeTab) {
                MainActivity.TAB_PERTAMA -> updateSliderData(slider, value, SliderColor.warna1)
                MainActivity.TAB_KEDUA -> updateSliderData(slider, value, SliderColor.warna2)
            }
            MainActivity.updateColor()
        }
        holder.slMerah.addOnChangeListener(listener)
        holder.slHijau.addOnChangeListener(listener)
        holder.slBiru.addOnChangeListener(listener)
        holder.slMerah.value = listWarna[position].merah
        holder.slHijau.value = listWarna[position].hijau
        holder.slBiru.value = listWarna[position].biru
    }

    override fun getItemCount(): Int = listWarna.size
}