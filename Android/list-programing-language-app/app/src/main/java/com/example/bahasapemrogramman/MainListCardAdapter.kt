package com.example.bahasapemrogramman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MainListCardAdapter(val listPL: ArrayList<ProgrammingLanguage>): RecyclerView.Adapter<MainListCardAdapter.ViewHolder>() {
    private lateinit var onItemSelected: OnItemSelected

    fun setOnItemSelected(onItemSelected: OnItemSelected) {
        this.onItemSelected = onItemSelected
    }

    class  ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.tv_nama)
        val tvDetail: TextView = view.findViewById(R.id.tv_short_detail)
        val ivLogo: ImageView = view.findViewById(R.id.iv_language_image)
        val btnDetail: Button = view.findViewById(R.id.btn_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.programming_list_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pL: ProgrammingLanguage = listPL[position]
        holder.tvNama.text = pL.nama
        holder.tvDetail.text = pL.detail
        holder.btnDetail.setOnClickListener { onItemSelected.itemSelected(position, pL.nama) }
        holder.ivLogo.contentDescription = pL.nama
        Glide.with(holder.itemView.context)
            .load(pL.foto)
            .apply(RequestOptions().override(200, 200))
            .into(holder.ivLogo)
    }

    override fun getItemCount(): Int {
        return listPL.size
    }

    interface OnItemSelected {
        fun itemSelected(position: Int, name: String)
    }

}