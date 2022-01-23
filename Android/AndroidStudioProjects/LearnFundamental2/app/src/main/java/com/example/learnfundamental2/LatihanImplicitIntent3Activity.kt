package com.example.learnfundamental2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.learnfundamental2.databinding.ActivityLatihanImplicitIntent3Binding
import com.example.learnfundamental2.dummydata.Dummy
import com.example.learnfundamental2.dummyresultactivity.DummyResult2Activity
import com.example.learnfundamental2.dummyresultactivity.DummyResultActivity
import java.io.Serializable

class LatihanImplicitIntent3Activity : AppCompatActivity() {

    companion object {
        private const val TAG = "LatihanImplicitIntent3"
    }

    private val binding by lazy { ActivityLatihanImplicitIntent3Binding.inflate(layoutInflater) }
    private val dummyResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        try {
            when (result.resultCode) {
                123 -> {
                    val data = result.data?.getSerializableExtra(DummyResultActivity.EXTRA_DUMMY_RESULT)?.let {
                        it as Dummy
                    } ?: Dummy("Tanpa Nama", 0)
                    Log.d(TAG, "name: ${data.name}, nim: ${data.nim}")
                }
                456 -> {
                    val data = result.data?.getSerializableExtra(DummyResult2Activity.EXTRA_DATA)?.let {
                        it as Dummy
                    } ?: Dummy("Tanpa Nama", 0)
                    Log.d(TAG, "name: ${data.name}, nim: ${data.nim}")
                }
            }
        } catch (err: Throwable) {
            println(err)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.latihanImplicitIntent3BtnButton1.setOnClickListener {
            dummyResultLauncher.launch(Intent(this, DummyResultActivity::class.java))
        }
        binding.latihanImplicitIntent3BtnButton2.setOnClickListener {
            dummyResultLauncher.launch(Intent(this, DummyResult2Activity::class.java))
        }

    }
}