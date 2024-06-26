package com.example.plugins

import com.example.routes.gambar
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        gambar()
        // Static plugin. Try to access `/static/index.html`
        staticResources(
            remotePath = "/",
            basePackage = "static"
        )
    }
}
