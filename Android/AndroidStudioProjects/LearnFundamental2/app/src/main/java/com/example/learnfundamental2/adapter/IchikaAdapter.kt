package com.example.learnfundamental2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.learnfundamental2.R
import com.example.learnfundamental2.dummydata.IchikaPicture

class IchikaAdapter : RecyclerView.Adapter<IchikaAdapter.IchikaViewHolder>() {

    inner class IchikaViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview){
        val ichikaImageView: ImageView = itemView.findViewById(R.id.ivListIchikaPicture)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IchikaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_ichika_picture, parent, false)
        return IchikaViewHolder(view)
    }

    override fun onBindViewHolder(holder: IchikaViewHolder, position: Int) {
        holder.ichikaImageView.setImageResource(IchikaPicture.picture[position])
    }

    override fun getItemCount(): Int = IchikaPicture.picture.size
}