package com.eltonjhony.eventapp.data.mapper

import com.eltonjhony.eventapp.data.local.entities.ImageEntity
import com.eltonjhony.eventapp.domain.EventImage

object EventImageToEntityMapper : DataMapper<EventImage, ImageEntity>() {

    override fun transform(entity: EventImage): ImageEntity {

        return ImageEntity(
            imageUrl = entity.imageUrl,
            height = entity.height,
            width = entity.height,
            ratio = entity.ratio
        )
    }

    override fun transform(entities: List<EventImage>?): List<ImageEntity> {
        return entities?.mapIndexed { index, eventImage ->
            val entity = transform(eventImage)
            entity.imageId = "${index}-${eventImage.eventId}"
            entity.eventId = eventImage.eventId
            entity
        } ?: emptyList()
    }
}

object EntityToEventImageMapper : DataMapper<ImageEntity, EventImage>() {

    override fun transform(entity: ImageEntity): EventImage {

        return EventImage(
            imageUrl = entity.imageUrl,
            eventId = entity.eventId ?: "",
            height = entity.height,
            width = entity.height,
            ratio = entity.ratio
        )

    }

    override fun transform(entities: List<ImageEntity>?): List<EventImage> {
        return entities?.map { transform(it) } ?: emptyList()
    }

}