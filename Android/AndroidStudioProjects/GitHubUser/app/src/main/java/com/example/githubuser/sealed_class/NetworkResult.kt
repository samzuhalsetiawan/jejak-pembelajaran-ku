package com.example.githubuser.sealed_class

sealed class NetworkResult<T> {
    class ResultSuccess<T>(val data: T) : NetworkResult<T>()
    class ResultError<T>(val httpCode: Int, val message: String?) : NetworkResult<T>()
    class ResultException<T>(val err: Throwable) : NetworkResult<T>()
}
