package com.example.todoapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.adapter.TodoWeeklyAdapter
import com.example.todoapp.databinding.FragmentTodoInOneWeekBinding
import com.example.todoapp.models.Hari
import com.example.todoapp.models.TodoList
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TodoInOneWeekFragment : Fragment(R.layout.fragment_todo_in_one_week) {

    private lateinit var binding: FragmentTodoInOneWeekBinding
    var currentTab = 0
    var previousTab = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoInOneWeekBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vpFragmentTodoInOneWeek.adapter = TodoWeeklyAdapter()

        TabLayoutMediator(binding.tlFragmentTodoInOneWeek, binding.vpFragmentTodoInOneWeek) { tab: TabLayout.Tab, i: Int ->
            tab.text = Hari.values()[i].name
        }.attach()

        binding.tlFragmentTodoInOneWeek.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                currentTab = tab?.position ?: currentTab
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                previousTab = tab?.position ?: previousTab
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                currentTab = tab?.position ?: currentTab
            }
        })

    }

}