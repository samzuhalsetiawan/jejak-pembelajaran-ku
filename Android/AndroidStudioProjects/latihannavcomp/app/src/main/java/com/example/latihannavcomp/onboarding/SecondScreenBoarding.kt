package com.example.latihannavcomp.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.latihannavcomp.R
import com.example.latihannavcomp.databinding.FragmentSecondScreenBoardingBinding

class SecondScreenBoarding(private val nextButtonListener: View.OnClickListener) : Fragment() {

    private lateinit var binding: FragmentSecondScreenBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_second_screen_boarding, container, false)
        binding = FragmentSecondScreenBoardingBinding.bind(view)

        binding.tvButtonNext2.setOnClickListener(nextButtonListener)

        return binding.root
    }

}