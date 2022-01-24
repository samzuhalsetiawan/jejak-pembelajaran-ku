package com.example.learnfundamental2

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.learnfundamental2.databinding.ActivityLatihanAlertDialogBinding

class LatihanAlertDialogActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLatihanAlertDialogBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val alertAddContact = AlertDialog.Builder(this).apply {
            setTitle("Add Contact")
            setIcon(R.drawable.ic_add_contact)
            setMessage("Tambahkan Mr. SamZ ke Kontak ?")
            setPositiveButton("Tambahkan"){ _: DialogInterface, _: Int ->
                Toast.makeText(this@LatihanAlertDialogActivity, "SamZ ditambahkan", Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("Batalkan"){ _: DialogInterface, _: Int ->
                Toast.makeText(this@LatihanAlertDialogActivity, "Batal ditambahkan", Toast.LENGTH_SHORT).show()
            }
            create()
        }

        val singleChoiceAlert = AlertDialog.Builder(this)
            .setTitle("Single Choice Alert")
            .setSingleChoiceItems(arrayOf("Item 1", "Item 2", "Item 3"), 0) { _: DialogInterface, i: Int ->
                when (i) {
                    0 -> Toast.makeText(this, "You Selected Item 1", Toast.LENGTH_SHORT).show()
                    1 -> Toast.makeText(this, "You Selected Item 2", Toast.LENGTH_SHORT).show()
                    2 -> Toast.makeText(this, "You Selected Item 3", Toast.LENGTH_SHORT).show()
                }
            }
            .setPositiveButton("Oke") { _: DialogInterface, _: Int ->
                Toast.makeText(this, "Oke", Toast.LENGTH_SHORT).show()
            }
            .create()

        val multipleChoiceAlert = AlertDialog.Builder(this).also {
            it.apply {
                setTitle("Multiple Choice Alert")
            }
            it.setMultiChoiceItems(arrayOf("Item 1", "Item 2", "Item 3"), booleanArrayOf(false, true, false))
            { _: DialogInterface, i: Int, b: Boolean ->
                when (i) {
                    0 -> {
                        when {
                            b -> Toast.makeText(this, "you check Item 1", Toast.LENGTH_SHORT).show()
                            else -> Toast.makeText(this, "you uncheck Item 1", Toast.LENGTH_SHORT).show()
                        }
                    }
                    1 -> {
                        when {
                            b -> Toast.makeText(this, "you check Item 2", Toast.LENGTH_SHORT).show()
                            else -> Toast.makeText(this, "you uncheck Item 2", Toast.LENGTH_SHORT).show()
                        }
                    }
                    2 -> {
                        when {
                            b -> Toast.makeText(this, "you check Item 3", Toast.LENGTH_SHORT).show()
                            else -> Toast.makeText(this, "you uncheck Item 3", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            it.setPositiveButton("Oke") { _: DialogInterface, _: Int ->
                Toast.makeText(this, "Oke", Toast.LENGTH_SHORT).show()
            }
            it.create()
        }

        binding.btnAddContact.setOnClickListener {
            alertAddContact.show()
        }
        binding.btnSingleChoice.setOnClickListener {
            singleChoiceAlert.show()
        }
        binding.btnMultipleChoice.setOnClickListener {
            multipleChoiceAlert.show()
        }

    }
}