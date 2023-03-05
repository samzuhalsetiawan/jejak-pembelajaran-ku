package com.example.latihannavcomp.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.latihannavcomp.R
import com.example.latihannavcomp.databinding.FragmentFirstScreenBoardingBinding

class FirstScreenBoarding(private val buttonListener: View.OnClickListener) : Fragment() {
    private lateinit var binding: FragmentFirstScreenBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first_screen_boarding, container, false)
        binding = FragmentFirstScreenBoardingBinding.bind(view)

        binding.tvButtonNext1.setOnClickListener(buttonListener)

        return binding.root
    }

}