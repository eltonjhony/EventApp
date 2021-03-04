package com.eltonjhony.eventapp.domain.repository

import com.eltonjhony.eventapp.domain.Event
import com.eltonjhony.eventapp.infrastructure.ResultWrapper

interface WishListRepository {
    suspend fun addToWishList(event: Event): ResultWrapper<Int>
    suspend fun removeToWishList(event: Event): ResultWrapper<Int>
    suspend fun fetchWishList(): ResultWrapper<List<Event>>
    suspend fun getWishListRowCount(): ResultWrapper<Int>
}