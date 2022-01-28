package com.example.learnfundamental2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.learnfundamental2.adapter.IchikaAdapter
import com.example.learnfundamental2.databinding.ActivityLatihanTabLayoutBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class LatihanTabLayoutActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLatihanTabLayoutBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.vp2IchikaVP.adapter = IchikaAdapter()

        TabLayoutMediator(binding.tlIchikaVP, binding.vp2IchikaVP) { tab, position ->
            tab.text = "Tab ${position + 1}"
        }.attach()

        binding.tlIchikaVP.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Toast.makeText(this@LatihanTabLayoutActivity, "Selected ${tab?.text}", Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Toast.makeText(this@LatihanTabLayoutActivity, "UnSelected ${tab?.text}", Toast.LENGTH_SHORT).show()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Toast.makeText(this@LatihanTabLayoutActivity, "ReSelected ${tab?.text}", Toast.LENGTH_SHORT).show()
            }
        })

    }
}