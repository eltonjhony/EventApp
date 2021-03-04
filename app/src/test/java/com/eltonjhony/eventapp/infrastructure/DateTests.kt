package com.eltonjhony.eventapp.infrastructure

import com.eltonjhony.eventapp.infrastructure.extensions.dayOfWeek
import com.eltonjhony.eventapp.infrastructure.extensions.formatWith
import com.eltonjhony.eventapp.infrastructure.extensions.parse
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DateTests {

    @Test
    fun `get day of week from date`() {

        val date = "2021-03-04T19:00:00Z".parse("yyy-MM-dd'T'HH:mm:ss'Z'")
        val dayOfWeek = date?.dayOfWeek()

        assert(dayOfWeek == 5)
    }

    @Test
    fun `get formatted date from date`() {

        val date = "2021-03-04T19:00:00Z".parse("yyy-MM-dd'T'HH:mm:ss'Z'")
        val formattedStr = date?.formatWith("yyyy-MM-dd")

        assert(formattedStr == "2021-03-04")
    }

}