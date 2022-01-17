package com.example.latihanrecyclerview2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(val items: List<String>): RecyclerView.Adapter<ListAdapter.ListHolder>() {

    class ListHolder(val view: View): RecyclerView.ViewHolder(view) {
        val tv = view.findViewById<TextView>(R.id.tv_list_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ListHolder(view)
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.tv.text = items[position]
    }

    override fun getItemCount(): Int = items.size
}