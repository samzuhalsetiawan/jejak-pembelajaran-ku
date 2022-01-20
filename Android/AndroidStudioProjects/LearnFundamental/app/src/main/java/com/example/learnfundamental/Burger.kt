package com.example.learnfundamental

import java.io.Serializable

data class Burger(
    val daging: String,
    val toppings: MutableList<String>
) : Serializable
