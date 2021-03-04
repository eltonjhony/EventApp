package com.eltonjhony.eventapp.presentation.features.listevents

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltonjhony.eventapp.domain.Event
import com.eltonjhony.eventapp.domain.interactor.GetEventsUseCase
import com.eltonjhony.eventapp.domain.interactor.GetEventsUseCase.Params
import com.eltonjhony.eventapp.infrastructure.Resource
import com.eltonjhony.eventapp.infrastructure.ResultWrapper.*
import com.eltonjhony.eventapp.infrastructure.extensions.*
import kotlinx.coroutines.launch

class ListEventsViewModel(private val getEventsUseCase: GetEventsUseCase) : ViewModel() {

    val eventsResource = MutableLiveData<Resource<List<Event>>>()

    fun fetchEvents(page: Int = 1) {
        eventsResource.loading()
        viewModelScope.launch {
            getEventsUseCase(Params(page)) {
                when (it) {
                    is Success -> eventsResource.setSuccess(it.data)
                    is NetworkError -> eventsResource.setNetworkFailure()
                    is DataNotFoundError -> eventsResource.setDataNotFound()
                    else -> eventsResource.setGenericFailure()
                }
            }
        }
    }

}