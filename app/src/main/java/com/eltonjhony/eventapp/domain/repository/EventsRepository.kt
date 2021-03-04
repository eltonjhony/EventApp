package com.eltonjhony.eventapp.domain.repository

import com.eltonjhony.eventapp.domain.Event
import com.eltonjhony.eventapp.infrastructure.ResultWrapper

interface EventsRepository {
    suspend fun fetchEventsBy(page: Int): ResultWrapper<List<Event>>
}