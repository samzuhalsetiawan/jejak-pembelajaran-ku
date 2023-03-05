package com.example.latihandicoding2

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity2 : AppCompatActivity() {

    companion object {
        const val TAG = "MAIN_ACTIVITY2"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val person = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(MainActivity.EXTRA_PARCEL, Person::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(MainActivity.EXTRA_PARCEL)
        }

        val person2: Person2? = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> intent.getSerializableExtra(MainActivity.EXTRA_SERIALIZE, Person2::class.java)
            else -> intent.getSerializableExtra(MainActivity.EXTRA_SERIALIZE) as? Person2
        }

        val person3 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(MainActivity.EXTRA_PARCEL2, Person3::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(MainActivity.EXTRA_PARCEL2)
        }

        val person4 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(MainActivity.EXTRA_PARCEL3, Person4::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(MainActivity.EXTRA_PARCEL3)
        }

        if (person != null) Log.d(MainActivity.TAG, person.toString())
        if (person2 != null) Log.d(MainActivity.TAG, person2.toString())
        if (person3 != null) Log.d(MainActivity.TAG, person3.toString())
        if (person4 != null) Log.d(MainActivity.TAG, person4.toString())


    }
}