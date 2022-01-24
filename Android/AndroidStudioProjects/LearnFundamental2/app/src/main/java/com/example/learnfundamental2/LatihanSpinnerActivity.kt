package com.example.learnfundamental2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.get
import com.example.learnfundamental2.databinding.ActivityLatihanSpinnerBinding

class LatihanSpinnerActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLatihanSpinnerBinding.inflate(layoutInflater) }

    companion object {
        private const val TAG = "LatihanSpinner"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.spBulan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@LatihanSpinnerActivity,
                    "You Selected ${resources.getStringArray(R.array.nama_nama_bulan)[position]}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        binding.root.setOnLongClickListener {
            binding.tvSpinSelected.text =
                resources.getStringArray(R.array.nama_nama_bulan)[binding.spBulan.selectedItemPosition].toString()
            true
        }

    }
}