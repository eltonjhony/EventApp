package com.eltonjhony.eventapp.data.repository

import com.eltonjhony.eventapp.data.repository.datasources.LocalWishListDataSource
import com.eltonjhony.eventapp.domain.Event
import com.eltonjhony.eventapp.domain.repository.WishListRepository
import com.eltonjhony.eventapp.infrastructure.ResultWrapper

class WishListDataRepository(
    private val localWishListDataSource: LocalWishListDataSource
) : WishListRepository {

    override suspend fun addToWishList(event: Event): ResultWrapper<Int> =
        localWishListDataSource.addToWishList(event)

    override suspend fun removeToWishList(event: Event): ResultWrapper<Int> =
        localWishListDataSource.removeToWishList(event)

    override suspend fun fetchWishList(): ResultWrapper<List<Event>> =
        localWishListDataSource.getAll()

    override suspend fun getWishListRowCount(): ResultWrapper<Int> =
        localWishListDataSource.getRowCount()

}