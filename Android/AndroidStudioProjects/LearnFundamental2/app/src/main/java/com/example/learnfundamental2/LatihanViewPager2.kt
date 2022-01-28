package com.example.learnfundamental2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.learnfundamental2.adapter.IchikaAdapter
import com.example.learnfundamental2.databinding.ActivityLatihanViewPager2Binding

class LatihanViewPager2 : AppCompatActivity() {

    private val binding by lazy { ActivityLatihanViewPager2Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.vp2viewPager.adapter = IchikaAdapter()

    }
}