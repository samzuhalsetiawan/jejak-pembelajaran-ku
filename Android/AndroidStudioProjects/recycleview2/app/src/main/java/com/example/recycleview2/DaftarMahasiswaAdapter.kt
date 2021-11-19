package com.example.recycleview2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DaftarMahasiswaAdapter(val listMahasiswa: ArrayList<Mahasiswa>) : RecyclerView.Adapter<DaftarMahasiswaAdapter.DaftarMahasiswaHolderView>() {
    class DaftarMahasiswaHolderView(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_nama)
        val tvDetail: TextView = view.findViewById(R.id.tv_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaftarMahasiswaHolderView {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.daftar_mahasiswa, parent, false)
        return DaftarMahasiswaHolderView(view)
    }

    override fun onBindViewHolder(holder: DaftarMahasiswaHolderView, position: Int) {
        holder.tvName.text = listMahasiswa[position].nama
        holder.tvDetail.text = listMahasiswa[position].detail
    }

    override fun getItemCount(): Int {
        return listMahasiswa.size
    }
}