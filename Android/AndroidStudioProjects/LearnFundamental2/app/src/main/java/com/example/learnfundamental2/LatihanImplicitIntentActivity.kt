package com.example.learnfundamental2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.learnfundamental2.databinding.ActivityLatihanImplicitIntentBinding

class LatihanImplicitIntentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLatihanImplicitIntentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLatihanImplicitIntentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPilihFoto.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it, 0)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 0) {
            val uri = data?.data
            binding.ivFotoPilihan.setImageURI(uri)
        }
    }

}