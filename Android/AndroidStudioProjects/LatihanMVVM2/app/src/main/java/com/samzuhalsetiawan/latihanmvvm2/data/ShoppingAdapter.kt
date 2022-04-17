package com.samzuhalsetiawan.latihanmvvm2.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samzuhalsetiawan.latihanmvvm2.R
import com.samzuhalsetiawan.latihanmvvm2.data.db.entities.ShoppingItem
import com.samzuhalsetiawan.latihanmvvm2.databinding.ShoppingListBinding
import com.samzuhalsetiawan.latihanmvvm2.ui.shoppinglist.ShoppingViewModel

class ShoppingAdapter(
    var shoppingList: List<ShoppingItem>,
    private val shoppingViewModel: ShoppingViewModel
): RecyclerView.Adapter<ShoppingAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ShoppingListBinding.bind(view)
        val tvItemName = binding.tvItemName
        val tvItemAmount = binding.tvItemAmount
        val ivAdd = binding.ivAdd
        val ivMinus = binding.ivMinus
        val ivDelete = binding.ivDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.shopping_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = shoppingList[position]
        holder.tvItemName.text = currentItem.name
        holder.tvItemAmount.text = currentItem.amount.toString()
        holder.ivDelete.setOnClickListener {
            shoppingViewModel.delete(currentItem)
        }
        holder.ivAdd.setOnClickListener {
            currentItem.amount++
            shoppingViewModel.upsert(currentItem)
        }
        holder.ivMinus.setOnClickListener {
            if (currentItem.amount > 0) {
                currentItem.amount--
                shoppingViewModel.upsert(currentItem)
            }
        }
    }

    override fun getItemCount(): Int = shoppingList.size
}