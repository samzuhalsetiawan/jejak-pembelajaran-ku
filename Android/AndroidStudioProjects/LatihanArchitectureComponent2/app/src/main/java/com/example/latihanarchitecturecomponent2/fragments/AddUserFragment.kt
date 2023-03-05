package com.example.latihanarchitecturecomponent2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.latihanarchitecturecomponent2.R
import com.example.latihanarchitecturecomponent2.data.User
import com.example.latihanarchitecturecomponent2.data.UserViewModel
import com.example.latihanarchitecturecomponent2.databinding.FragmentAddUserBinding

class AddUserFragment : Fragment() {

    private lateinit var binding: FragmentAddUserBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_user, container, false)
            .also { binding = FragmentAddUserBinding.bind(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]

        binding.btnAddUser.setOnClickListener { addUser() }
    }

    private fun doInputCheck(): Boolean {
        val userName = binding.etUserName.text.toString()
        val userDesc = binding.etUserDesc.text.toString()
        return !(userName.trim().isBlank() || userDesc.trim().isBlank())
    }

    private fun addUser() {
        if(!doInputCheck()) {
            Toast.makeText(this.context, "Mohon isi nama dan deskripsi", Toast.LENGTH_LONG).show()
            return
        }
        val userName = binding.etUserName.text.toString()
        val userDesc = binding.etUserDesc.text.toString()
        val user = User(id = null, userName, userDesc)
        userViewModel.insertUser(user)
        Toast.makeText(this.context, "User Added", Toast.LENGTH_LONG).show()
    }
}