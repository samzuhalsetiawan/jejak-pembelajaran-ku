package com.samzuhalsetiawan.latihanaksescontentprovider.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samzuhalsetiawan.latihanaksescontentprovider.R
import com.samzuhalsetiawan.latihanaksescontentprovider.databinding.DictionaryListBinding
import com.samzuhalsetiawan.latihanaksescontentprovider.model.Dictionary

class DictionaryAdapter(private val dictionaryList: ArrayList<Dictionary>):
    RecyclerView.Adapter<DictionaryAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = DictionaryListBinding.bind(view)
        val tvDictionaryId = binding.tvDictionaryId
        val tvDictionaryWord = binding.tvDictionaryWord
        val tvDictionaryLocale = binding.tvDictionaryLocale
        val tvDictionaryAppId = binding.tvDictionaryAppID
        val tvDictionaryFrequency = binding.tvDictionaryFrequency
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dictionary_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvDictionaryId.text = dictionaryList[position].id
        holder.tvDictionaryWord.text = dictionaryList[position].word
        holder.tvDictionaryLocale.text = dictionaryList[position].locale
        holder.tvDictionaryAppId.text = dictionaryList[position].appId
        holder.tvDictionaryFrequency.text = dictionaryList[position].frequency
    }

    override fun getItemCount(): Int = dictionaryList.size
}