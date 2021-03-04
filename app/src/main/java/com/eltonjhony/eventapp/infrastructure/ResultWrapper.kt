package com.eltonjhony.eventapp.infrastructure

sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T): ResultWrapper<T>()
    data class GenericError(val error: Error? = null): ResultWrapper<Nothing>()
    object NetworkError: ResultWrapper<Nothing>()
    object DataNotFoundError: ResultWrapper<Nothing>()
}