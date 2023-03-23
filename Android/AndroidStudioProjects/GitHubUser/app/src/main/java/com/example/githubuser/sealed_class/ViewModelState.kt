package com.example.githubuser.sealed_class

sealed class ViewModelState<T> {
    class Loading<T> : ViewModelState<T>()
    class Success<T>(val data: T): ViewModelState<T>()
    class Error<T>(val displayMessage: String): ViewModelState<T>()
}
