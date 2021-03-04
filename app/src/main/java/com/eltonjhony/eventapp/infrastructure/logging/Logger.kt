package com.eltonjhony.eventapp.infrastructure.logging

import android.util.Log
import com.eltonjhony.eventapp.BuildConfig

class Logger private constructor(private val TAG: String) {

    private var priority: Int = Log.ERROR

    fun d(message: String): Logger {
        this.priority = Log.DEBUG
        if (BuildConfig.DEBUG) {
            Log.println(priority, TAG, message)
        }
        return this
    }

    fun e(message: String): Logger {
        this.priority = Log.ERROR
        if (BuildConfig.DEBUG) {
            Log.println(priority, TAG, message)
        }
        return this
    }

    fun i(message: String): Logger {
        this.priority = Log.INFO
        if (BuildConfig.DEBUG) {
            Log.println(priority, TAG, message)
        }
        return this
    }

    fun withCause(cause: Throwable) {
        Log.println(priority, TAG, cause.localizedMessage)
    }

    fun withCause(error: Error?) {
        error?.let { Log.println(priority, TAG, it.toString()) }
    }

    companion object {
        fun withTag(tag: String): Logger {
            return Logger(tag)
        }
    }

}