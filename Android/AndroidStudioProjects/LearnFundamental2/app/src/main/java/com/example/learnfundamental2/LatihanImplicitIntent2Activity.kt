package com.example.learnfundamental2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.learnfundamental2.databinding.ActivityLatihanImplicitIntent2Binding

class LatihanImplicitIntent2Activity : AppCompatActivity() {

    private val binding by lazy { ActivityLatihanImplicitIntent2Binding.inflate(layoutInflater) }
    private val launcher: ActivityResultLauncher<String> = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.ivFotoPilihan.setImageURI(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnPilihFoto.setOnClickListener {
            launcher.launch("image/*")
        }

    }
}