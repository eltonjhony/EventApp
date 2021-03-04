package com.eltonjhony.eventapp.presentation.features.wishlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltonjhony.eventapp.domain.Event
import com.eltonjhony.eventapp.domain.interactor.*
import com.eltonjhony.eventapp.infrastructure.Resource
import com.eltonjhony.eventapp.infrastructure.ResultWrapper.Success
import com.eltonjhony.eventapp.infrastructure.extensions.setDataNotFound
import com.eltonjhony.eventapp.infrastructure.extensions.setGenericFailure
import com.eltonjhony.eventapp.infrastructure.extensions.setSuccess
import kotlinx.coroutines.launch

class WishListViewModel(
    private val addToWishListUseCase: AddToWishListUseCase,
    private val removeToWishListUseCase: RemoveToWishListUseCase,
    private val getWishListRowCountUseCase: GetWishListRowCountUseCase,
    private val fetchWishListUseCase: FetchWishListUseCase
) : ViewModel() {

    val countResource = MutableLiveData<Resource<Int>>()
    val wishListResource = MutableLiveData<Resource<List<Event>>>()

    fun persistWishList(event: Event) =
        if (event.isFavorite) addToWishList(event) else removeToWishList(event)

    fun getWishListRowCount() {
        viewModelScope.launch {
            getWishListRowCountUseCase(None()) {
                if (it is Success)
                    countResource.setSuccess(it.data)
            }
        }
    }

    fun fetchWishList() {
        viewModelScope.launch {
            fetchWishListUseCase(None()) {
                when (it) {
                    is Success -> wishListResource.setSuccess(it.data)
                    else -> wishListResource.setDataNotFound()
                }
            }
        }
    }

    private fun addToWishList(event: Event) {
        viewModelScope.launch {
            addToWishListUseCase(AddToWishListUseCase.Params(event)) {
                when (it) {
                    is Success -> countResource.setSuccess(it.data)
                    else -> countResource.setGenericFailure()
                }
            }
        }
    }

    private fun removeToWishList(event: Event) {
        viewModelScope.launch {
            removeToWishListUseCase(RemoveToWishListUseCase.Params(event)) {
                when (it) {
                    is Success -> countResource.setSuccess(it.data)
                    else -> countResource.setGenericFailure()
                }
            }
        }
    }

}