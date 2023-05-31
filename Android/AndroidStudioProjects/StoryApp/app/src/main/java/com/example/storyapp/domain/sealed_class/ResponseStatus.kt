package com.example.storyapp.domain.sealed_class

sealed class ResponseStatus <T> {
    class Success <T> (val data: T): ResponseStatus<T>()
    class Error <T> (val throwable: Throwable): ResponseStatus<T>()
}
