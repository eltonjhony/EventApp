package com.eltonjhony.eventapp

import android.content.Context
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import com.eltonjhony.eventapp.domain.Event
import com.eltonjhony.eventapp.domain.EventImage
import com.eltonjhony.eventapp.domain.EventLocation
import com.eltonjhony.eventapp.infrastructure.extensions.parse
import com.eltonjhony.eventapp.presentation.features.eventdetails.EventDetailsActivity
import com.eltonjhony.eventapp.utils.ViewActions.waitFor
import com.eltonjhony.eventapp.utils.ViewAssertions.withCustomViewAtElement
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class EventDetailsActivityTest {

    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Rule
    @JvmField
    val rule: ActivityTestRule<EventDetailsActivity> =
        object : ActivityTestRule<EventDetailsActivity>(EventDetailsActivity::class.java) {
            override fun getActivityIntent(): Intent {
                return Intent(context, EventDetailsActivity::class.java).apply {
                    putExtra("EVENT_EXTRA_PARAM", event)
                }
            }
        }

    @Before
    fun setup() {
        onView(isRoot()).perform(waitFor())
    }

    @Test
    fun checkImmutableEventItemsVisibility() {
        onView(withId(R.id.carouselView)).check(matches(isDisplayed()))
        onView(withId(R.id.eventNameTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.favoriteView)).check(matches(isDisplayed()))
        onView(withId(R.id.dateAndTimeLabel)).check(matches(isDisplayed()))
        onView(withId(R.id.dateAndTimeText)).check(matches(isDisplayed()))
        onView(withId(R.id.locationView)).check(matches(isDisplayed()))
    }

    @Test
    fun checkEventViewPopulated() {
        onView(withId(R.id.eventNameTextView)).check(matches(withText(event.name)))
        onView(withId(R.id.dateAndTimeLabel)).check(matches(withText("Date & Time")))
        onView(withId(R.id.dateAndTimeText)).check(matches(withText(event.getDateAndTime())))
        onView(
            withCustomViewAtElement(
                R.id.eventLocationLabel,
                R.id.locationView
            )
        ).check(matches(withText("Event Location")))
        onView(
            withCustomViewAtElement(
                R.id.eventLocationTextView,
                R.id.locationView
            )
        ).check(matches(withText(event.location.toString())))
    }

    @Test
    fun checkLocationExternalGpsButtonClicked() {
        onView(
            withCustomViewAtElement(
                R.id.gpsButton,
                R.id.locationView
            )
        ).check(matches(withText("Get Location (GPS)")))
            .perform(click())

        waitFor()
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).pressBack()
    }

    companion object {
        val event = Event(
            "Z698xZC2Z178vav",
            "VIVID Grand Show",
            listOf(
                EventImage(
                    imageUrl = "https://s1.ticketm.net/dam/a/564/9492b73e-2446-4b58-8011-83368dbc1564_744561_TABLET_LANDSCAPE_LARGE_16_9.jpg",
                    eventId = "Z698xZC2Z178vav",
                    ratio = "2048",
                    width = 2048,
                    height = 1152
                ),
                EventImage(
                    imageUrl = "https://s1.ticketm.net/dam/a/564/9492b73e-2446-4b58-8011-83368dbc1564_744561_ARTIST_PAGE_3_2.jpg",
                    eventId = "Z698xZC2Z178vav",
                    ratio = "3_2",
                    width = 305,
                    height = 203
                ),
                EventImage(
                    imageUrl = "https://s1.ticketm.net/dam/a/564/9492b73e-2446-4b58-8011-83368dbc1564_744561_RETINA_PORTRAIT_16_9.jpg",
                    eventId = "Z698xZC2Z178vav",
                    ratio = "16_9",
                    width = 640,
                    height = 360
                )
            ),
            "2021-03-04T19:00:00Z".parse("yyy-MM-dd'T'HH:mm:ss'Z'"),
            EventLocation(
                address = "line1",
                city = "Berlin",
                latitude = "84284",
                longitude = "489498389"
            ),
            isFavorite = false
        )
    }

}