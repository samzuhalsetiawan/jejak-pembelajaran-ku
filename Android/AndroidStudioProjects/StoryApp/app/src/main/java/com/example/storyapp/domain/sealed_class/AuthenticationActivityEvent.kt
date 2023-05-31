package com.example.storyapp.domain.sealed_class

sealed class AuthenticationActivityEvent {
    object ShowLoading: AuthenticationActivityEvent()
    object RedirectUserToMain: AuthenticationActivityEvent()
    object UserNotLoginYet: AuthenticationActivityEvent()
    class OnError(val error: Throwable): AuthenticationActivityEvent()
    class RegisterSuccess(val email: String): AuthenticationActivityEvent()
}
