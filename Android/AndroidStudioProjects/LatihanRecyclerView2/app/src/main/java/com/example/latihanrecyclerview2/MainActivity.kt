package com.example.latihanrecyclerview2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val names = createBanyakNamaRandom(100)
        recyclerView = findViewById(R.id.rv_activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ListAdapter(names)
    }

    private fun createBanyakNamaRandom(banyakNama: Int): List<String> {
        val hurufVokal = listOf('a', 'i', 'u', 'e', 'o')
        val huruf = ('a').rangeTo('z').toList()
        return List(banyakNama) {
            val nama = StringBuilder()
            val banyakSukuKata = Random.nextInt(1, 4)
            repeat(banyakSukuKata) {
                val banyakHuruf = Random.nextInt(3, 6)
                val word = StringBuilder()
                repeat(banyakHuruf) {
                    if (it > 0) {
                        word.append(
                            when (word[it - 1]) {
                                !in hurufVokal -> hurufVokal.random()
                                else -> huruf.random()
                            }
                        )
                    } else word.append(huruf.random())
                }
                nama.apply {
                    append(word)
                    if (it != banyakSukuKata - 1) append(' ')
                }
            }
            nama.toString()
        }
    }
}