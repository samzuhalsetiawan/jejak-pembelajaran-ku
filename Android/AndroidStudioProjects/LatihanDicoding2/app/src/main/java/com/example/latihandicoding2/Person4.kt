package com.example.latihandicoding2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person4(
    val name: String,
    val kode: Int,
    val angkaFavorite: List<Int>
) : Parcelable
