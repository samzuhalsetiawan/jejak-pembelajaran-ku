package com.example.githubuser.exceptions

class ApiIOException(
    private val causedBy: Throwable
) : Throwable() {
    override val cause: Throwable
        get() = causedBy
}