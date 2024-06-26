package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Gambar(
    val name: String,
    val imgUrl: String
)
