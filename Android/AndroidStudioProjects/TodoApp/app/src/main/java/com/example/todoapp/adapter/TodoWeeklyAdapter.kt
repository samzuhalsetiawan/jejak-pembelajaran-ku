package com.example.todoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.ListTodoDiHari
import com.example.todoapp.databinding.ViewPager2TodoWeeklyBinding
import com.example.todoapp.models.Hari

class TodoWeeklyAdapter :
    RecyclerView.Adapter<TodoWeeklyAdapter.ViewHolder>() {

    companion object {
        private lateinit var adapterRvSenin: RecyclerView.Adapter<*>
        private lateinit var adapterRvSelasa: RecyclerView.Adapter<*>
        private lateinit var adapterRvRabu: RecyclerView.Adapter<*>
        private lateinit var adapterRvKamis: RecyclerView.Adapter<*>
        private lateinit var adapterRvJumat: RecyclerView.Adapter<*>
        private lateinit var adapterRvSabtu: RecyclerView.Adapter<*>
        private lateinit var adapterRvMinggu: RecyclerView.Adapter<*>

        fun notifyItemInsertedToAdapterOf(hari: Hari) {
            when (hari) {
                Hari.SENIN -> {
                    if (this::adapterRvSenin.isInitialized)
                        adapterRvSenin.notifyItemInserted(ListTodoDiHari.senin.lastIndex)
                }
                Hari.SELASA -> {
                    if (this::adapterRvSelasa.isInitialized)
                        adapterRvSelasa.notifyItemInserted(ListTodoDiHari.selasa.lastIndex)
                }
                Hari.RABU -> {
                    if (this::adapterRvRabu.isInitialized)
                        adapterRvRabu.notifyItemInserted(ListTodoDiHari.rabu.lastIndex)
                }
                Hari.KAMIS -> {
                    if (this::adapterRvKamis.isInitialized)
                        adapterRvKamis.notifyItemInserted(ListTodoDiHari.kamis.lastIndex)
                }
                Hari.JUMAT -> {
                    if (this::adapterRvJumat.isInitialized)
                        adapterRvJumat.notifyItemInserted(ListTodoDiHari.jumat.lastIndex)
                }
                Hari.SABTU -> {
                    if (this::adapterRvSabtu.isInitialized)
                        adapterRvSabtu.notifyItemInserted(ListTodoDiHari.sabtu.lastIndex)
                }
                Hari.MINGGU -> {
                    if (this::adapterRvMinggu.isInitialized)
                        adapterRvMinggu.notifyItemInserted(ListTodoDiHari.minggu.lastIndex)
                }
            }
        }
        fun notifyItemRemovedToAdapterOf(hari: Hari, position: Int) {
            when (hari) {
                Hari.SENIN -> {
                    if (this::adapterRvSenin.isInitialized)
                        adapterRvSenin.notifyItemRemoved(position)
                }
                Hari.SELASA -> {
                    if (this::adapterRvSelasa.isInitialized)
                        adapterRvSelasa.notifyItemRemoved(position)
                }
                Hari.RABU -> {
                    if (this::adapterRvRabu.isInitialized)
                        adapterRvRabu.notifyItemRemoved(position)
                }
                Hari.KAMIS -> {
                    if (this::adapterRvKamis.isInitialized)
                        adapterRvKamis.notifyItemRemoved(position)
                }
                Hari.JUMAT -> {
                    if (this::adapterRvJumat.isInitialized)
                        adapterRvJumat.notifyItemRemoved(position)
                }
                Hari.SABTU -> {
                    if (this::adapterRvSabtu.isInitialized)
                        adapterRvSabtu.notifyItemRemoved(position)
                }
                Hari.MINGGU -> {
                    if (this::adapterRvMinggu.isInitialized)
                        adapterRvMinggu.notifyItemRemoved(position)
                }
            }
        }
        fun notifyItemChangedToAdapterOf(hari: Hari, position: Int) {
            when (hari) {
                Hari.SENIN -> {
                    if (this::adapterRvSenin.isInitialized)
                        adapterRvSenin.notifyItemChanged(position)
                }
                Hari.SELASA -> {
                    if (this::adapterRvSelasa.isInitialized)
                        adapterRvSelasa.notifyItemChanged(position)
                }
                Hari.RABU -> {
                    if (this::adapterRvRabu.isInitialized)
                        adapterRvRabu.notifyItemChanged(position)
                }
                Hari.KAMIS -> {
                    if (this::adapterRvKamis.isInitialized)
                        adapterRvKamis.notifyItemChanged(position)
                }
                Hari.JUMAT -> {
                    if (this::adapterRvJumat.isInitialized)
                        adapterRvJumat.notifyItemChanged(position)
                }
                Hari.SABTU -> {
                    if (this::adapterRvSabtu.isInitialized)
                        adapterRvSabtu.notifyItemChanged(position)
                }
                Hari.MINGGU -> {
                    if (this::adapterRvMinggu.isInitialized)
                        adapterRvMinggu.notifyItemChanged(position)
                }
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewPager2TodoWeeklyBinding.bind(view)
        val rvTodo = binding.rvViewPager2TodoWeekly
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_pager2_todo_weekly, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val adapter = when (Hari.values()[position]) {
            Hari.SENIN -> TodoListAdapter(ListTodoDiHari.senin).also { adapterRvSenin = it }
            Hari.SELASA -> TodoListAdapter(ListTodoDiHari.selasa).also { adapterRvSelasa = it }
            Hari.RABU -> TodoListAdapter(ListTodoDiHari.rabu).also { adapterRvRabu = it }
            Hari.KAMIS -> TodoListAdapter(ListTodoDiHari.kamis).also { adapterRvKamis = it }
            Hari.JUMAT -> TodoListAdapter(ListTodoDiHari.jumat).also { adapterRvJumat = it }
            Hari.SABTU -> TodoListAdapter(ListTodoDiHari.sabtu).also { adapterRvSabtu = it }
            Hari.MINGGU -> TodoListAdapter(ListTodoDiHari.minggu).also { adapterRvMinggu = it }
        }
        holder.rvTodo.adapter = adapter
        holder.rvTodo.layoutManager = LinearLayoutManager(holder.rvTodo.context)
    }

    override fun getItemCount(): Int = Hari.values().size
}