package com.example.storyapp.data.source.remote.dto

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import com.example.storyapp.data.models.Story
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

data class StoryDto(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    val createdAt: String,
    val lat: Double?,
    val lon: Double?
)

fun StoryDto.toStory(): Story {
    val storyPosition = if (lat != null && lon != null) LatLng(lat, lon) else null
    val rawSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply { timeZone = TimeZone.getTimeZone("UTC") }
    val readableDate = SimpleDateFormat("dd/MM/yyyy HH:mm O", Locale.getDefault()).format(rawSdf.parse(createdAt))
    return Story(
        id, name, description, photoUrl, readableDate, storyPosition
    )
}
