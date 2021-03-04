package com.eltonjhony.eventapp.domain.interactor

import com.eltonjhony.eventapp.domain.Event
import com.eltonjhony.eventapp.domain.repository.EventsRepository
import com.eltonjhony.eventapp.infrastructure.ResultWrapper

class GetEventsUseCase(private val eventsRepository: EventsRepository) :
    UseCase<List<Event>, GetEventsUseCase.Params>() {

    override suspend fun run(params: Params): ResultWrapper<List<Event>> =
        eventsRepository.fetchEventsBy(params.page)

    data class Params(val page: Int)
}