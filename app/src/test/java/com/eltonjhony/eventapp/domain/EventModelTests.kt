package com.eltonjhony.eventapp.domain

import com.eltonjhony.eventapp.infrastructure.extensions.parse
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*

@RunWith(JUnit4::class)
class EventModelTests {

    @Test
    fun `event should get formatted dates`() {
        val event = Event(
            "4948",
            "test",
            emptyList(),
            "2021-03-04T19:00:00Z".parse("yyy-MM-dd'T'HH:mm:ss'Z'"),
            null,
            isFavorite = false
        )

        val month = event.getMonth()
        val day = event.getDay()
        val dayOfWeek = event.getDayOfWeek()
        val startTime = event.getStartTime()
        val dateAndTime = event.getDateAndTime()

        assert(month == "Mar")
        assert(day == 5)
        assert(dayOfWeek == "Thu")
        assert(startTime == "19:00 PM")
        assert(dateAndTime == "2021, Mar 04 - 19:00 PM")
    }

    @Test
    fun `event should get formatted location`() {
        val event = Event(
            "4948",
            "test",
            emptyList(),
            Date(),
            EventLocation(
                address = "line1",
                city = "Berlin",
                latitude = "84284",
                longitude = "489498389"
            ),
            isFavorite = false
        )

        val city = event.getCity()
        val location = event.location.toString()

        assert(city == "Berlin")
        assert(location == "line1 - Berlin")
    }

    @Test
    fun `event should get correctly images`() {
        val event = Event(
            "4948",
            "test",
            listOf(
                EventImage(
                    imageUrl = "https://jdjdlknk.com",
                    eventId = "1",
                    ratio = "4_3",
                    width = 302,
                    height = 500
                ),
                EventImage(
                    imageUrl = "https://jdjdlkddnk.com",
                    eventId = "2",
                    ratio = "16_4",
                    width = 1200,
                    height = 700
                ),
                EventImage(
                    imageUrl = "https://jdjdlddfeknk.com",
                    eventId = "3",
                    ratio = "3_2",
                    width = 220,
                    height = 180
                )
            ),
            null,
            null,
            isFavorite = false
        )

        val thumb = event.getThumbImageUrl()
        val banners = event.getBanners()

        assert(thumb == "https://jdjdlknk.com")
        assert(banners?.size == 1)
    }

}