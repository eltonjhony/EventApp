package com.eltonjhony.eventapp.data.repository.datasources

import com.eltonjhony.eventapp.data.local.EventDao
import com.eltonjhony.eventapp.data.mapper.EntityToEventMapper
import com.eltonjhony.eventapp.data.mapper.EventImageToEntityMapper
import com.eltonjhony.eventapp.data.mapper.EventToEntityMapper
import com.eltonjhony.eventapp.domain.Event
import com.eltonjhony.eventapp.infrastructure.ResultWrapper
import com.eltonjhony.eventapp.infrastructure.ResultWrapper.DataNotFoundError
import com.eltonjhony.eventapp.infrastructure.ResultWrapper.Success

class LocalWishListDataSource(private val eventDao: EventDao) {

    fun addToWishList(event: Event): ResultWrapper<Int> {

        val eventEntity = EventToEntityMapper.transform(event)
        eventDao.insert(eventEntity)

        val eventImages = EventImageToEntityMapper.transform(event.images)
        eventImages.map { eventDao.insert(it) }
        return Success(eventDao.getRowCount())
    }

    fun removeToWishList(event: Event): ResultWrapper<Int> {
        eventDao.delete(EventToEntityMapper.transform(event))
        return Success(eventDao.getRowCount())
    }

    fun getRowCount(): ResultWrapper<Int> =
        Success(eventDao.getRowCount())

    fun getAll(): ResultWrapper<List<Event>> {
        val wishList = eventDao.getAll()
        return if (wishList.isEmpty())
            DataNotFoundError
        else
            Success(EntityToEventMapper.transform(wishList)
                .sortedByDescending { it.date })
    }

}