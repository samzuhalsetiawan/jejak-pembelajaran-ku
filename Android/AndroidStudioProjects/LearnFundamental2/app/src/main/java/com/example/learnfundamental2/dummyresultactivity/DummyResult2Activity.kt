package com.example.learnfundamental2.dummyresultactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.learnfundamental2.R
import com.example.learnfundamental2.databinding.ActivityDummyResult2Binding
import com.example.learnfundamental2.dummydata.Dummy
import java.io.Serializable

class DummyResult2Activity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
    }

    private val binding by lazy { ActivityDummyResult2Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.dummyResult2BtnSendResult.setOnClickListener {
//            Intent().putExtra(EXTRA_DATA, Dummy("Setiawan", 200)).also {
//                setResult(456, it)
//                finish()
//            }
            Intent().putExtra(EXTRA_DATA, object : Serializable {
                val name = "Udin"
                val nim = 200
            }).also {
                setResult(456, it)
                finish()
            }
        }

    }
}