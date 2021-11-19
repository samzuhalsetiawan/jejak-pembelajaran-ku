package com.example.recycleview3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvMain: RecyclerView
    var listMahasiswa: ArrayList<Mahasiswa> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listMahasiswa = ambilListMahasiswa()
        rvMain = findViewById(R.id.rv_main)
        rvMain.layoutManager = LinearLayoutManager(this)
        rvMain.adapter = CardMhsAdapter(listMahasiswa)

    }

    fun ambilListMahasiswa(): ArrayList<Mahasiswa> {
        val mahasiswaList = resources.getStringArray(R.array.list_nama)
        val detailList = resources.getStringArray(R.array.list_detail)
        val list: ArrayList<Mahasiswa> = arrayListOf()
        for (indeks in mahasiswaList.indices) {
            list.add(Mahasiswa(mahasiswaList[indeks], detailList[indeks]))
        }
        return list
    }

}