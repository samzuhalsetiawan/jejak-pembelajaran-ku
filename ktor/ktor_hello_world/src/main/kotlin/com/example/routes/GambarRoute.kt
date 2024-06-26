package com.example.routes

import com.example.data.model.Gambar
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private const val BASE_URL = "http://localhost:8080"
private val gambars = listOf(
    Gambar("Gambar1", "$BASE_URL/img/desain_kreatif.jpg"),
    Gambar("Gambar2", "$BASE_URL/img/ilmu_pendidikan.jpg"),
    Gambar("Gambar3", "$BASE_URL/img/jasa.jpg"),
)

fun Route.gambar() {
    get("/gambar") {
        call.respond(
            HttpStatusCode.OK, gambars.random()
        )
    }
}