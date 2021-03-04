package com.eltonjhony.eventapp.infrastructure.extensions

import androidx.lifecycle.MutableLiveData
import com.eltonjhony.eventapp.infrastructure.Resource
import com.eltonjhony.eventapp.infrastructure.ResourceState.*

fun <T> MutableLiveData<Resource<T>>.setSuccess(data: T? = null) {
    postValue(Resource(Success, data))
}

fun <T> MutableLiveData<Resource<T>>.loading() =
    postValue(Resource(Loading))

fun <T> MutableLiveData<Resource<T>>.setGenericFailure(error: Error? = null) =
    postValue(Resource(GenericError, error = error))

fun <T> MutableLiveData<Resource<T>>.setDataNotFound(error: Error? = null) =
    postValue(Resource(DataNotFoundError, error = error))

fun <T> MutableLiveData<Resource<T>>.setNetworkFailure(error: Error? = null) =
    postValue(Resource(NetworkError, error = error))