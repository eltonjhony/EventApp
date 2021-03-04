package com.eltonjhony.eventapp.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "event")
data class EventEntity(
    @PrimaryKey val id: String,
    val name: String?,
    val date: String?,
    @Embedded val location: LocationEntity?
) {
    constructor() : this("", null, null, null)
}

@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey val locationId: String,
    val address: String?,
    val city: String?,
    val longitude: String?,
    val latitude: String?
)

@Entity(tableName = "events_images")
data class EventsWithImages(
    @Embedded var event: EventEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "eventId"
    )
    var images: List<ImageEntity>
) {
    constructor() : this(EventEntity(), emptyList());
}

@Entity(tableName = "images")
data class ImageEntity(
    val imageUrl: String,
    val ratio: String,
    val width: Int,
    val height: Int
) {
    @PrimaryKey
    var imageId: String = ""
    var eventId: String? = null
}