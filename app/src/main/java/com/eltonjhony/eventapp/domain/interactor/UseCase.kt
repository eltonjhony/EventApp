package com.eltonjhony.eventapp.domain.interactor

import com.eltonjhony.eventapp.infrastructure.ResultWrapper
import com.eltonjhony.eventapp.infrastructure.ResultWrapper.GenericError
import com.eltonjhony.eventapp.infrastructure.ResultWrapper.NetworkError
import com.eltonjhony.eventapp.infrastructure.logging.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class UseCase<T, in Params> where T : Any? {

    protected abstract suspend fun run(params: Params): ResultWrapper<T>

    suspend operator fun invoke(params: Params, onResult: (ResultWrapper<T>) -> Unit) {
        runCatching {
            val result = withContext(Dispatchers.IO) {
                run(params)
            }
            logErrors(result)
            onResult(result)
        }.onFailure {
            Logger.withTag(UseCase::class.java.simpleName).withCause(it)
            onResult(GenericError(Error(it.message)))
        }
    }

    private fun logErrors(result: ResultWrapper<T>) {
        runCatching {
            if (result is GenericError)
                Logger.withTag(UseCase::class.java.simpleName).withCause(result.error)
            else if (result is NetworkError)
                Logger.withTag(UseCase::class.java.simpleName)
                    .withCause(Throwable("Network not Available at the moment"))
        }
    }

}

class None