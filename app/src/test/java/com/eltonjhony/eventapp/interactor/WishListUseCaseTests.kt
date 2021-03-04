package com.eltonjhony.eventapp.interactor

import com.eltonjhony.eventapp.domain.Event
import com.eltonjhony.eventapp.domain.interactor.*
import com.eltonjhony.eventapp.domain.repository.WishListRepository
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
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever

@RunWith(JUnit4::class)
class WishListUseCaseTests {

    @Mock
    lateinit var repository: WishListRepository

    lateinit var getWishListRowCountUseCase: GetWishListRowCountUseCase
    lateinit var fetchWishListUseCase: FetchWishListUseCase
    lateinit var addWishListUseCase: AddToWishListUseCase
    lateinit var removeToWishListUseCase: RemoveToWishListUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getWishListRowCountUseCase = GetWishListRowCountUseCase(repository)
        fetchWishListUseCase = FetchWishListUseCase(repository)
        addWishListUseCase = AddToWishListUseCase(repository)
        removeToWishListUseCase = RemoveToWishListUseCase(repository)
    }

    @Test
    fun `get wish list row count`() {
        GlobalScope.launch {
            whenever(repository.getWishListRowCount()).thenReturn(Success(2))

            getWishListRowCountUseCase(None()) {
                if (it is Success) {
                    assert(it.data == 2)
                } else {
                    Assert.fail()
                }
            }
        }
    }

    @Test
    fun `fetch wish list events with success`() {
        GlobalScope.launch {
            whenever(repository.fetchWishList()).thenReturn(
                Success(
                    listOf(
                        getMockEvent()
                    )
                )
            )

            fetchWishListUseCase(None()) {
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
    fun `add to wish list with success`() {
        GlobalScope.launch {
            whenever(
                repository.addToWishList(
                    getMockEvent()
                )
            ).thenReturn(Success(3))

            addWishListUseCase(AddToWishListUseCase.Params(getMockEvent())) {
                if (it is Success) {
                    assert(it.data == 3)
                } else {
                    Assert.fail()
                }
            }
        }
    }

    @Test
    fun `remove from wish list with success`() {
        GlobalScope.launch {
            whenever(
                repository.removeToWishList(
                    getMockEvent()
                )
            ).thenReturn(Success(1))

            removeToWishListUseCase(RemoveToWishListUseCase.Params(getMockEvent())) {
                if (it is Success) {
                    assert(it.data == 1)
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