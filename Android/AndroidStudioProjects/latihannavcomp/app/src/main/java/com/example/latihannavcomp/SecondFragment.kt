package com.example.latihannavcomp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.latihannavcomp.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    val args: SecondFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_second, container, false)
        val binding = FragmentSecondBinding.bind(view)
        binding.tvTwo.text = args.angka.toString()
        binding.tvTwo.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        return binding.root
    }

}