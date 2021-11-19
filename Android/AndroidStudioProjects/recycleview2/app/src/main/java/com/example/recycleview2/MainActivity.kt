package com.example.recycleview2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvMain: RecyclerView
    private var listMahasiswa: ArrayList<Mahasiswa> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listMahasiswa = getAllMahasiswa()
        rvMain = findViewById(R.id.rv_main)
        rvMain.layoutManager = LinearLayoutManager(this)
        rvMain.adapter = DaftarMahasiswaAdapter(listMahasiswa)

    }

    fun getAllMahasiswa(): ArrayList<Mahasiswa> {
        val mahasiswaList = resources.getStringArray(R.array.mahasiswa_list)
        val detailList = resources.getStringArray(R.array.detail_list)
        val mylist: ArrayList<Mahasiswa> = arrayListOf()
        for (indeks in mahasiswaList.indices) {
            mylist.add(Mahasiswa(mahasiswaList[indeks], detailList[indeks]))
        }
        return mylist
    }

}