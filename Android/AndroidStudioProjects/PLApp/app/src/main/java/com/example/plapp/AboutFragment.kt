package com.example.plapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.plapp.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).supportActionBar?.show()
        }
        binding =
            FragmentAboutBinding.bind(inflater.inflate(R.layout.fragment_about, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide
            .with(this)
            .load(R.drawable.profile)
            .circleCrop()
            .into(binding.ivProfilePicture)
    }

}