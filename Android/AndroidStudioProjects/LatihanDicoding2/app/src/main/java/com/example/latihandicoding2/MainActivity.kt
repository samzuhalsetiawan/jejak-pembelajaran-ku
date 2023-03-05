package com.example.latihandicoding2

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MAIN_ACTIVITY"
        const val EXTRA_PARCEL = "EXTRA_PARCEL"
        const val EXTRA_PARCEL2 = "EXTRA_PARCEL_2"
        const val EXTRA_PARCEL3 = "EXTRA_PARCEL_3"
        const val EXTRA_SERIALIZE = "EXTRA_SERIALIZE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            Log.d(TAG, it.toString())
        }

        val launcher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                it.data?.let {
                    Log.d(TAG, it.data?.toString() ?: "Null")
                }
            } else { Log.d(TAG, "Result Failed") }
        }

        findViewById<Button>(R.id.btnClickMe).setOnClickListener {
//            launcher.launch("image/*")
//            val intent = Intent(Intent.ACTION_GET_CONTENT)
//            intent.type = "image/*"
//            launcher2.launch(Intent.createChooser(intent, "Pilih Gambar"))
//            launcher2.launch(intent)
//            val number = "082250550043"
//            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
//            startActivity(intent)

            val myIntent = Intent(this, MainActivity2::class.java)
            val person = Person("Sam", 123, null, 321)
            val person2 = Person2("Zuhal", 123)
            val tesAngka = arrayListOf(1,2,3)
            val person3 = Person3("Sam", 123, tesAngka)
            val person4 = Person4("Sam", 321, listOf(4,5,6))

            myIntent.putExtra(EXTRA_PARCEL, person)
            myIntent.putExtra(EXTRA_PARCEL2, person3)
            myIntent.putExtra(EXTRA_PARCEL3, person4)
            myIntent.putExtra(EXTRA_SERIALIZE, person2)
            startActivity(myIntent)

        }

    }
}