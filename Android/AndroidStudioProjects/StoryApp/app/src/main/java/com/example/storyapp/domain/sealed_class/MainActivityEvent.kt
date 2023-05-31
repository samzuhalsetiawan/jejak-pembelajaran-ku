package com.example.storyapp.domain.sealed_class

sealed class MainActivityEvent<T> {
    class OnLoading<T>: MainActivityEvent<T>()
    class OnData<T>(val data: T): MainActivityEvent<T>()
    class OnPostingSuccess<T>: MainActivityEvent<T>()
    class OnLogout<T>: MainActivityEvent<T>()
    class OnError<T>(val displayErrorMessage: String): MainActivityEvent<T>()
}