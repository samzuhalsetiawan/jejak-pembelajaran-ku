package com.example.latihanlayoutinflater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.doOnAttach

class MainActivity : AppCompatActivity() {

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private val console = object {
        fun log(message: Any) = Log.e("DEBUG", message.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.viewMerah).setOnClickListener {
            showToast("view merah")
            console.log("view merah")
        }

        findViewById<LinearLayout>(R.id.llHijau).setOnClickListener {
            showToast("linear layout hijau")
            console.log("linear layout hijau")
        }

        val secondLayoutView = layoutInflater.inflate(R.layout.second_layout, findViewById<LinearLayout>(R.id.llMain), false)
        secondLayoutView.doOnAttach {
            showToast("linear layout biru attach")
            console.log("linear layout biru attach")
        }
        findViewById<LinearLayout>(R.id.llMain).addView(secondLayoutView)
        console.log(findViewById<LinearLayout>(R.id.llMain).childCount)

        console.log("Root View llbiru: ${secondLayoutView.rootView::class.java.simpleName}")
        console.log((secondLayoutView.rootView is LinearLayout).toString())

        secondLayoutView.findViewById<LinearLayout>(R.id.llBiru).setOnClickListener {
            showToast("linear layout biru")
            console.log("linear biru")
            console.log((it.height).toString())
        }

        findViewById<TextView>(R.id.tvTest).text = "World"

        findViewById<LinearLayout>(R.id.llMain).getChildAt(0).setOnClickListener {
            when (it.id) {
                R.id.tvTest -> console.log("Yap Benar itu adalah tvTest")
                else -> console.log("Bukan, itu tidak benar")
            }
        }
        findViewById<LinearLayout>(R.id.llMain).getChildAt(1).setOnClickListener {
            when (it.id) {
                R.id.tvTest -> console.log("Yap Benar itu adalah tvTest")
                else -> console.log("Bukan, itu tidak benar")
            }
        }

    }
}