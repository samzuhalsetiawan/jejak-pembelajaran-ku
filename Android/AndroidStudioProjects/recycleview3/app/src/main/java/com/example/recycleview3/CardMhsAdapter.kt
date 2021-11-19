package com.example.recycleview3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CardMhsAdapter(val listMahasiswa: ArrayList<Mahasiswa>) : RecyclerView.Adapter<CardMhsAdapter.CardMhsViewHolder>() {
    class CardMhsViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.tv_nama)
        val tvDetail: TextView = view.findViewById(R.id.tv_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardMhsViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.card_mahasiswa, parent, false)
        return CardMhsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardMhsViewHolder, position: Int) {
        holder.tvNama.text = listMahasiswa[position].nama
        holder.tvDetail.text = listMahasiswa[position].detail
    }

    override fun getItemCount(): Int {
        return listMahasiswa.size
    }
}