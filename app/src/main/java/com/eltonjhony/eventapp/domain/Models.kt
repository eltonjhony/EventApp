package com.eltonjhony.eventapp.domain

import android.os.Parcelable
import com.eltonjhony.eventapp.infrastructure.Constants
import com.eltonjhony.eventapp.infrastructure.extensions.dayOfWeek
import com.eltonjhony.eventapp.infrastructure.extensions.formatWith
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Event(
    val id: String,
    val name: String?,
    val images: List<EventImage> = emptyList(),
    val date: Date?,
    val location: EventLocation?,
    var isFavorite: Boolean
) : Parcelable {

    fun getMonth(): String? = date?.formatWith(Constants.DatePattern.MONTH_FORMAT)

    fun getDayOfWeek(): String? = date?.formatWith(Constants.DatePattern.DAY_OF_WEEK_FORMAT)

    fun getDay(): Int? = date?.dayOfWeek()

    fun getStartTime(): String? = date?.formatWith(Constants.DatePattern.TIME_FORMAT)

    fun getCity(): String? = location?.city

    fun getThumbImageUrl(): String? =
        images.firstOrNull { it.width in 301..499 }?.imageUrl

    fun getBanners(): List<String>? =
        images.filter { it.width in 300..600 }.map { it.imageUrl }

    fun getDateAndTime(): String? =
        date?.formatWith(Constants.DatePattern.DATE_AND_TIME_FORMAT)
}

@Parcelize
data class EventImage(
    val imageUrl: String,
    val eventId: String,
    val ratio: String,
    val width: Int,
    val height: Int
) : Parcelable


@Parcelize
data class EventLocation(
    val address: String?,
    val city: String?,
    val longitude: String?,
    val latitude: String?
) : Parcelable {

    override fun toString(): String {
        return "$address - $city"
    }
}

data class Genre(
    val id: String,
    val name: String
) {

    companion object {
        fun getDefault(): List<Genre> {
            return listOf(
                Genre("KnvZfZ7v7la", "Spectacular"),
                Genre("KnvZfZ7vAeE", "Fairs & Festivals"),
                Genre("KnvZfZ7vAd1", "Equestrian"),
                Genre("KnvZfZ7vA1n", "Family"),
                Genre("KnvZfZ7vAe1", "Comedy"),
                Genre("KnvZfZ7vAeA", "Rock"),
                Genre("KnvZfZ7vAvv", "Alternative"),
                Genre("KnvZfZ7vAA7", "Volleyball"),
                Genre("KnvZfZ7vAvl", "Other")
            )
        }
    }

}
