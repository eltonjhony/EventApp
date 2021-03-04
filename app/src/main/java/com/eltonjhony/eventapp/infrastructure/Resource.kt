package com.eltonjhony.eventapp.infrastructure

open class Resource<out T> constructor(
    val state: ResourceState,
    val data: T? = null,
    val error: Error? = null
) {

    var hasBeenHandled = false
        private set

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): Resource<T>? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            this
        }
    }

}