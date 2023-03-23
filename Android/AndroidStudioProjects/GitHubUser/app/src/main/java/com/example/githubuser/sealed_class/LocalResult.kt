package com.example.githubuser.sealed_class

sealed class LocalResult<T> {
    class ResultSuccess<T>(val data: T): LocalResult<T>()
    class ResultException<T>(val throwable: Throwable) : LocalResult<T>()
}
