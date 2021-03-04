package com.eltonjhony.eventapp.data.repository.datasources

import com.eltonjhony.eventapp.data.local.GenreFilterLocalPreferences.getAsFilter
import com.eltonjhony.eventapp.data.mapper.ResponseToEventMapper
import com.eltonjhony.eventapp.data.remote.EventService
import com.eltonjhony.eventapp.data.remote.NetworkHandler
import com.eltonjhony.eventapp.domain.Event
import com.eltonjhony.eventapp.infrastructure.Constants
import com.eltonjhony.eventapp.infrastructure.ResultWrapper
import com.eltonjhony.eventapp.infrastructure.ResultWrapper.*

class CloudEventDataSource(
    private val service: EventService,
    private val networkHandler: NetworkHandler
) {

    suspend fun fetchEventsBy(page: Int): ResultWrapper<List<Event>> {
        return when {
            networkHandler.isConnected -> {
                runCatching {

                    val responseWrapper = service.fetchEventsBy(
                        page = page,
                        countryCode = Constants.SearchFilter.COUNTRY_CODE,
                        genreIds = getAsFilter(),
                        includeTBA = Constants.SearchFilter.NO,
                        includeTBD = Constants.SearchFilter.NO,
                        includeTest = Constants.SearchFilter.NO,
                        size = Constants.SearchFilter.PAGE_SIZE
                    )

                    val events = responseWrapper.response?.events
                    if (!events.isNullOrEmpty() || responseWrapper.page.searchFinished()) {
                        Success(ResponseToEventMapper.transform(events))
                    } else {
                        DataNotFoundError
                    }

                }.getOrElse { GenericError(Error(it.localizedMessage)) }
            }
            else -> NetworkError
        }
    }

}