package com.example.githubuser.exceptions

class ApiUnexpectedResponseException(
    private val responseCode: Int,
    private val responseMessage: String?
) : Throwable() {
    override val message: String
        get() = "Server response with unexpected result, code: $responseCode, message: $responseMessage"
}