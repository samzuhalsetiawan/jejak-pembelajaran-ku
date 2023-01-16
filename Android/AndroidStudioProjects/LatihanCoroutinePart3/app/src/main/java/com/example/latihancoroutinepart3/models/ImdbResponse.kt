package com.example.latihancoroutinepart3.models

data class ImdbResponse (
    val searchType: String,
    val expression: String,
    val results: List<Movie>,
    val errorMessage: String
        )