package com.eltonjhony.eventapp.interactor

import com.eltonjhony.eventapp.domain.Event
import com.eltonjhony.eventapp.domain.interactor.GetEventsUseCase
import com.eltonjhony.eventapp.domain.repository.EventsRepository
import com.eltonjhony.eventapp.infrastructure.ResultWrapper.GenericError
import com.eltonjhony.eventapp.infrastructure.ResultWrapper.Success
import com.eltonjhony.eventapp.infrastructure.extensions.parse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetEventsUseCaseTests {

    @Mock
    lateinit var repository: EventsRepository

    lateinit var usecase: GetEventsUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        usecase = GetEventsUseCase(repository)
    }

    @Test
    fun `get events list with success`() {
        GlobalScope.launch {
            Mockito.`when`(repository.fetchEventsBy(1)).thenReturn(Success(listOf(getMockEvent())))

            usecase(GetEventsUseCase.Params(1)) {
                if (it is Success) {
                    assert(it.data.size == 1)
                    assert(it.data[0].id == "4948")
                } else {
                    Assert.fail()
                }
            }
        }
    }

    @Test
    fun `get events list with error`() {
        GlobalScope.launch {
            val error = Error("generic error message")
            Mockito.`when`(repository.fetchEventsBy(1)).thenReturn(GenericError(error))

            usecase(GetEventsUseCase.Params(1)) {
                if (it is GenericError) {
                    assert(it.error == error)
                } else {
                    Assert.fail()
                }
            }
        }
    }

    private fun getMockEvent(): Event {
        return Event(
            "4948",
            "test",
            emptyList(),
            "2021-03-04T19:00:00Z".parse("yyy-MM-dd'T'HH:mm:ss'Z'"),
            null,
            isFavorite = false
        )
    }

}