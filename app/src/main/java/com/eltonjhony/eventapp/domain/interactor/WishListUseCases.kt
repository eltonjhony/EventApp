package com.eltonjhony.eventapp.domain.interactor

import com.eltonjhony.eventapp.domain.Event
import com.eltonjhony.eventapp.domain.repository.WishListRepository
import com.eltonjhony.eventapp.infrastructure.ResultWrapper

class AddToWishListUseCase(private val repository: WishListRepository) :
    UseCase<Int, AddToWishListUseCase.Params>() {

    override suspend fun run(params: Params): ResultWrapper<Int> =
        repository.addToWishList(params.event)

    data class Params(val event: Event)
}

class RemoveToWishListUseCase(private val repository: WishListRepository) :
    UseCase<Int, RemoveToWishListUseCase.Params>() {

    override suspend fun run(params: Params): ResultWrapper<Int> =
        repository.removeToWishList(params.event)

    data class Params(val event: Event)
}

class FetchWishListUseCase(private val repository: WishListRepository) :
    UseCase<List<Event>, None>() {

    override suspend fun run(params: None): ResultWrapper<List<Event>> =
        repository.fetchWishList()

}

class GetWishListRowCountUseCase(private val repository: WishListRepository) :
    UseCase<Int, None>() {
    
    override suspend fun run(params: None): ResultWrapper<Int> =
        repository.getWishListRowCount()

}