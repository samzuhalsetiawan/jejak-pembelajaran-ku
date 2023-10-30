package com.example.storyapp.domain.sealed_class

sealed class MapsPageEvent {
    object OnLoading: MapsPageEvent()
    object OnCloseLoading: MapsPageEvent()
    class OnError(val throwable: Throwable): MapsPageEvent()
}
