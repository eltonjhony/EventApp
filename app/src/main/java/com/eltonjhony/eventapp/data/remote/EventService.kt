package com.eltonjhony.eventapp.data.remote

import com.eltonjhony.eventapp.data.remote.model.EventResponseWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface EventService {

    @GET("events")
    suspend fun fetchEventsBy(
        @Query("countryCode") countryCode: String,
        @Query("includeTBA") includeTBA: String,
        @Query("includeTBD") includeTBD: String,
        @Query("includeTest") includeTest: String,
        @Query("genreId") genreIds: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): EventResponseWrapper

}