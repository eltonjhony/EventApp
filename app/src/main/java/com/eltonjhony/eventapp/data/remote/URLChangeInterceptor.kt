package com.eltonjhony.eventapp.data.remote

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

object URLChangeInterceptor : Interceptor {

    private var httpUrl: HttpUrl? = null

    fun setInterceptor(url: String) {
        httpUrl = url.toHttpUrlOrNull()
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        httpUrl?.let {
            original = original.newBuilder()
                .url(it)
                .build()
        }
        return chain.proceed(original)
    }

}