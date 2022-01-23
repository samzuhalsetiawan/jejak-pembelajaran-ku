package com.example.learnfundamental2.dummyresultactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.learnfundamental2.R
import com.example.learnfundamental2.databinding.ActivityDummyResultBinding
import com.example.learnfundamental2.dummydata.Dummy
import java.io.Serializable

class DummyResultActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDummyResultBinding.inflate(layoutInflater) }

    companion object {
        const val EXTRA_DUMMY_RESULT = "EXTRA_DUMMY_RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.dummyResultBtnSendResult.setOnClickListener {
            Intent().putExtra(EXTRA_DUMMY_RESULT, Dummy("Sam Zuhal Setiawan", 2005176043)).also {
                setResult(123, it)
                finish()
            }
        }

    }
}