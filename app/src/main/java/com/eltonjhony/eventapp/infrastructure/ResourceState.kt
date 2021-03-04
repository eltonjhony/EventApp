package com.eltonjhony.eventapp.infrastructure

sealed class ResourceState {
    object Loading : ResourceState()
    object Success : ResourceState()
    object NetworkError : ResourceState()
    object GenericError : ResourceState()
    object DataNotFoundError : ResourceState()
}