package com.example.latihannavcomp.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.latihannavcomp.R
import com.example.latihannavcomp.databinding.FragmentThirdScreenBoardingBinding

class ThirdScreenBoarding(private val finnishButtonListener: View.OnClickListener) : Fragment() {

    private lateinit var binding: FragmentThirdScreenBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_third_screen_boarding, container, false)
        binding = FragmentThirdScreenBoardingBinding.bind(view)
        binding.tvButtonFinnish.setOnClickListener(finnishButtonListener)
        return binding.root
    }

}