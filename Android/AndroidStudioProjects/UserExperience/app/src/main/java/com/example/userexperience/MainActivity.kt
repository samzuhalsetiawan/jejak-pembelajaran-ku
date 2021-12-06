package com.example.userexperience

import android.content.Context
import android.inputmethodservice.InputMethodService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.userexperience.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.editText.setOnKeyListener { view, keyCode, event ->
//            when (keyCode) {
//                KeyEvent.KEYCODE_1 -> {
//                    Toast.makeText(this, "Anda menekan 1", Toast.LENGTH_SHORT).show()
//                    false
//                }
//                KeyEvent.KEYCODE_COMMA -> {
//                    Toast.makeText(this, "Anda menekan koma", Toast.LENGTH_SHORT).show()
//                    false
//                }
//                else -> false
//            }
//        }
        binding.editText.setOnKeyListener {view, keyCode, _ -> handleKeyEvent(view, keyCode)}

    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_1) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

}