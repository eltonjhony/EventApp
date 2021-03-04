package com.eltonjhony.eventapp.data.mapper

import com.eltonjhony.eventapp.data.local.entities.EventEntity
import com.eltonjhony.eventapp.data.local.entities.EventsWithImages
import com.eltonjhony.eventapp.data.local.entities.LocationEntity
import com.eltonjhony.eventapp.data.remote.model.EventItem
import com.eltonjhony.eventapp.domain.Event
import com.eltonjhony.eventapp.domain.EventImage
import com.eltonjhony.eventapp.domain.EventLocation
import com.eltonjhony.eventapp.infrastructure.Constants
import com.eltonjhony.eventapp.infrastructure.extensions.formatWith
import com.eltonjhony.eventapp.infrastructure.extensions.parse

object EventToEntityMapper : DataMapper<Event, EventEntity>() {

    override fun transform(entity: Event): EventEntity {

        val location = entity.location?.let {
            LocationEntity(
                locationId = "${it.address} - ${it.city}",
                address = it.address,
                city = it.city,
                longitude = it.longitude,
                latitude = it.latitude
            )
        }

        return EventEntity(
            id = entity.id,
            name = entity.name,
            date = entity.date?.formatWith(Constants.DatePattern.DATE_TIME_FORMAT),
            location = location
        )
    }

    override fun transform(entities: List<Event>?): List<EventEntity> {
        return entities?.map { transform(it) } ?: emptyList()
    }

}

object EntityToEventMapper : DataMapper<EventsWithImages, Event>() {

    override fun transform(entity: EventsWithImages): Event {

        val location = entity.event.location?.let {
            EventLocation(
                address = it.address,
                city = it.city,
                longitude = it.longitude,
                latitude = it.latitude
            )
        }

        return Event(
            id = entity.event.id,
            name = entity.event.name,
            date = entity.event.date?.parse(Constants.DatePattern.DATE_TIME_FORMAT),
            location = location,
            images = EntityToEventImageMapper.transform(entity.images),
            isFavorite = true
        )
    }

    override fun transform(entities: List<EventsWithImages>?): List<Event> {
        return entities?.map { transform(it) } ?: emptyList()
    }

}

object ResponseToEventMapper : DataMapper<EventItem, Event>() {

    override fun transform(entity: EventItem): Event {

        val images = entity.images?.map {
            EventImage(
                imageUrl = it.url,
                eventId = entity.id,
                height = it.height,
                width = it.height,
                ratio = it.ratio
            )
        }

        val locations = entity.venueResponse?.venues?.map {
            EventLocation(
                address = it.address?.description,
                city = it.city?.name,
                longitude = it.location?.longitude,
                latitude = it.location?.latitude
            )
        }

        return Event(
            id = entity.id,
            name = entity.name,
            date = entity.dates?.eventStartDate?.dateTime?.parse(Constants.DatePattern.DATE_TIME_FORMAT),
            location = locations?.first(),
            images = images ?: emptyList(),
            isFavorite = false
        )
    }

    override fun transform(entities: List<EventItem>?): List<Event> {
        return entities?.map { transform(it) } ?: emptyList()
    }

}