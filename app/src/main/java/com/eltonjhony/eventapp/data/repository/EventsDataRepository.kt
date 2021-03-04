package com.eltonjhony.eventapp.data.repository

import com.eltonjhony.eventapp.data.repository.datasources.CloudEventDataSource
import com.eltonjhony.eventapp.data.repository.datasources.LocalWishListDataSource
import com.eltonjhony.eventapp.domain.Event
import com.eltonjhony.eventapp.domain.repository.EventsRepository
import com.eltonjhony.eventapp.infrastructure.ResultWrapper
import com.eltonjhony.eventapp.infrastructure.ResultWrapper.Success

class EventsDataRepository(
    private val cloudEventDataSource: CloudEventDataSource,
    private val localWishListDataSource: LocalWishListDataSource
) : EventsRepository {

    override suspend fun fetchEventsBy(page: Int): ResultWrapper<List<Event>> {
        val eventsResult = cloudEventDataSource.fetchEventsBy(page)
        mergeFavoriteEvents(eventsResult)
        return eventsResult
    }

    private fun mergeFavoriteEvents(
        eventsResult: ResultWrapper<List<Event>>
    ) {
        val wishListResult = localWishListDataSource.getAll()
        if (eventsResult is Success && wishListResult is Success) {
            val wishList = wishListResult.data.map { it.id }
            eventsResult.data.forEach { it.isFavorite = wishList.contains(it.id) }
        }
    }

}