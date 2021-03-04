package com.eltonjhony.eventapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class EventResponseWrapper(
    @SerializedName(value = "_embedded")
    val response: EventResponse?,
    val page: PageResponse
)

data class PageResponse(
    val size: Int,
    val totalElements: Int,
    val totalPages: Int,
    val number: Int
) {

    fun searchFinished(): Boolean =
        totalPages in 1..number
}

data class EventResponse(
    val events: List<EventItem>?
)

data class EventItem(
    val id: String,
    val name: String,
    val images: List<ImageResponse>?,
    val dates: DatesResponseWrapper?,
    @SerializedName(value = "_embedded")
    val venueResponse: VenueResponseWrapper?
)

data class ImageResponse(
    val url: String,
    val ratio: String,
    val width: Int,
    val height: Int
)

data class DatesResponseWrapper(
    @SerializedName(value = "start")
    val eventStartDate: DateResponse?,
    val timezone: String?
)

data class DateResponse(
    val localDate: String,
    val localTime: String,
    val dateTime: String
)

data class VenueResponseWrapper(
    val venues: List<VenueResponse>
)

data class VenueResponse(
    val city: CityResponse?,
    val address: AddressResponse?,
    val location: LocationResponse?
)

data class CityResponse(
    val name: String
)

data class AddressResponse(
    @SerializedName(value = "line1")
    val description: String
)

data class LocationResponse(
    val longitude: String,
    val latitude: String
)