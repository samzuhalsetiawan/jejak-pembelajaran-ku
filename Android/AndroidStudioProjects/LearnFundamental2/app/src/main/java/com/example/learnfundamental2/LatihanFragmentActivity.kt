package com.example.learnfundamental2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.learnfundamental2.databinding.ActivityLatihanFragmentBinding
import com.example.learnfundamental2.dummyfragment.FirstFragment
import com.example.learnfundamental2.dummyfragment.SecondFragment

class LatihanFragmentActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLatihanFragmentBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val firstFragment = FirstFragment()
        val secondFragmet = SecondFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flLatihanFragment, firstFragment)
            commit()
        }

        binding.btnFirstFragment.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flLatihanFragment, firstFragment)
                commit()
            }
        }

        binding.btnSecondFragment.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flLatihanFragment, secondFragmet)
                commit()
            }
        }

    }
}