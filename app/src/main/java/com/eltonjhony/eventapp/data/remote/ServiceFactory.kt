package com.eltonjhony.eventapp.data.remote

import com.eltonjhony.eventapp.infrastructure.Constants
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceFactory {

    fun getRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.Configs.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .create()
                )
            )
            .client(getClient())
            .build()

    private fun getClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(URLChangeInterceptor)
            .addInterceptor { chain ->
                val original = chain.request()

                val originalHttpUrl = original.url

                val newUrl = originalHttpUrl
                    .newBuilder()
                    .addQueryParameter(
                        Constants.Configs.API_KEY_PARAM,
                        Constants.Configs.API_KEY_VALUE
                    )
                    .build()

                val request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .method(original.method, original.body)
                    .url(newUrl)
                    .build()

                chain.proceed(request)
            }

        return builder.build()
    }
}