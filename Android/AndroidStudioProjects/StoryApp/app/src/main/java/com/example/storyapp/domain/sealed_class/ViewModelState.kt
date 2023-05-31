package com.example.storyapp.domain.sealed_class

sealed class ViewModelState<T>() {
    class OnLoading<T>(): ViewModelState<T>()
    class OnData<T>(val data: T): ViewModelState<T>()
    class OnError<T>(val displayErrorMessage: String): ViewModelState<T>()
}
