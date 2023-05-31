package com.example.storyapp.data.models

import com.google.android.gms.maps.model.LatLng

data class Story(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    val createdAt: String,
    val storyPosition: LatLng?
)