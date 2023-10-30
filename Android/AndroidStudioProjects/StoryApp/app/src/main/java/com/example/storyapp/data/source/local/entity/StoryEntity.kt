package com.example.storyapp.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.storyapp.data.models.Story
import com.google.android.gms.maps.model.LatLng

@Entity("story_table")
data class StoryEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val page: Int,
    @ColumnInfo("photo_url")
    val photoUrl: String,
    @ColumnInfo("created_at")
    val createdAt: String,
    val lat: Double?,
    val lon: Double?
)

fun StoryEntity.toStory(): Story {
    val storyPosition = if (lat != null && lon != null) LatLng(lat, lon) else null
    return Story(
        id, name, description, photoUrl, createdAt, storyPosition
    )
}

fun Story.toEntity(page: Int): StoryEntity {
    return StoryEntity(id, name, description, page, photoUrl, createdAt, storyPosition?.latitude, storyPosition?.longitude)
}