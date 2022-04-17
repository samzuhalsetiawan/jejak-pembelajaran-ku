package com.samzuhalsetiawan.latihancoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val job1 = GlobalScope.launch(Dispatchers.IO) {
            Log.e("TAG", "Hello From ${Thread.currentThread().name}")
            val answer = async { doNetworkCall() }
            val answer2 = async { doNetworkCall2() }
            Log.e("TAG", answer.await())
            Log.e("TAG", answer2.await())
//            withContext(Dispatchers.Main) {
//                findViewById<TextView>(R.id.tv1).text = answer
//            }
        }

        val time = measureTimeMillis {
            val job2 = runBlocking {
//                job1.join()
                delay(3000L)
                Log.e("TAG", "Hello From ${Thread.currentThread().name}")
                withContext(Dispatchers.IO) {
                    Log.e("TAG", "(tes1) Hello From ${Thread.currentThread().name}")
                }
                launch(Dispatchers.Default) {
                    Log.e("TAG", "(tes2) Hello From ${Thread.currentThread().name}")
                }
            }
//            runBlocking {
//                job2.join()
//                Log.e("TAG", "This will execute after all process above it")
//            }
        }
        Log.e("TAG", "runBlocking time: $time")

//        GlobalScope.launch(Dispatchers.IO) {
//            Log.e("TAG", "Do Network Call")
//            val result = withTimeout(1500L) {
//                doNetworkCall()
//            }
//            withContext(Dispatchers.Main) {
//                Log.e("TAG", "This network call need more time then expected, canceled")
//                Log.e("TAG", "Result: $result")
//            }
//        }

        lifecycleScope.launch {
            for (i in 1..10) {
                Log.e("TAG", "Calculating...")
                delay(1000L)
            }
        }

    }

    suspend fun doNetworkCall(): String {
        delay(3000L)
        return "This is the answer"
    }
    suspend fun doNetworkCall2(): String {
        delay(3000L)
        return "This is the answer2"
    }
}
