package com.example.latihanarchitecturecomponent2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanarchitecturecomponent2.R
import com.example.latihanarchitecturecomponent2.data.UserRvAdapter
import com.example.latihanarchitecturecomponent2.data.UserViewModel
import com.example.latihanarchitecturecomponent2.databinding.FragmentListUserBinding

class ListUserFragment : Fragment() {

    private lateinit var binding: FragmentListUserBinding
    private lateinit var userRvAdapter: UserRvAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_user, container, false)
            .also { binding = FragmentListUserBinding.bind(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userRvAdapter = UserRvAdapter()
        setupRecyclerView()
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        userViewModel.getAllUser().observe(requireActivity()) { userRvAdapter.allUsers = it }

        binding.fabAddUser.setOnClickListener { goToAddUserPage() }
    }

    private fun goToAddUserPage() {
        findNavController().navigate(R.id.action_listUserFragment_to_addUserFragment)
    }

    private fun setupRecyclerView() {
        binding.rvListUser.apply {
            adapter = userRvAdapter
            layoutManager = LinearLayoutManager(this@ListUserFragment.context)
        }
    }
}