package com.eltonjhony.eventapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.eltonjhony.eventapp.mock.AppStubber
import com.eltonjhony.eventapp.presentation.features.eventdetails.EventDetailsActivity
import com.eltonjhony.eventapp.presentation.features.listevents.ListEventsActivity
import com.eltonjhony.eventapp.presentation.features.wishlist.WishListActivity
import com.eltonjhony.eventapp.presentation.views.EventsListView
import com.eltonjhony.eventapp.utils.RecyclerViewMatcher.withRecyclerView
import com.eltonjhony.eventapp.utils.ViewAssertions.withCustomViewAtElement
import com.eltonjhony.eventapp.utils.ViewAssertions.withCustomViewOnRecyclerView
import com.eltonjhony.eventapp.utils.ViewAssertions.withFontSize
import com.github.tomakehurst.wiremock.common.ConsoleNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ListEventsActivityTest {

    @get:Rule
    var intentTestRule =
        IntentsTestRule(ListEventsActivity::class.java, true, false)

    @get:Rule
    var wireMockRule = WireMockRule(
        wireMockConfig()
            .port(8080)
            .notifier(ConsoleNotifier(true))
    )

    @Before
    fun setup() {
        AppStubber.stubTicketMasterResponseWithSuccess("ticketmaster_response.json")
    }

    @Test
    fun testEventCardClick() {
        intentTestRule.launchActivity(null)

        IdlingRegistry.getInstance().register(EventsListView.countingIdlingResource)

        onView(
            withRecyclerView(R.id.eventsRecyclerView)
                .atPositionOnView(0, R.id.eventItemView)
        ).perform(ViewActions.click())

        intended(hasComponent(EventDetailsActivity::class.java.name))
    }

    @Test
    fun checkEventItemTitleView() {
        intentTestRule.launchActivity(null)

        IdlingRegistry.getInstance().register(EventsListView.countingIdlingResource)

        onView(
            withRecyclerView(R.id.eventsRecyclerView)
                .atPositionOnView(0, R.id.eventNameTextView)
        ).check(matches(withText("VIVID Grand Show")))

        onView(
            withRecyclerView(R.id.eventsRecyclerView)
                .atPositionOnView(0, R.id.eventNameTextView)
        ).check(matches(withFontSize(16f)))

    }

    @Test
    fun checkEventItemStartTimeView() {
        intentTestRule.launchActivity(null)

        IdlingRegistry.getInstance().register(EventsListView.countingIdlingResource)

        onView(
            withRecyclerView(R.id.eventsRecyclerView)
                .atPositionOnView(0, R.id.eventStartLabel)
        ).check(matches(withText("Beginning")))

        onView(
            withRecyclerView(R.id.eventsRecyclerView)
                .atPositionOnView(0, R.id.eventStartLabel)
        ).check(matches(withFontSize(12f)))

        onView(
            withRecyclerView(R.id.eventsRecyclerView)
                .atPositionOnView(0, R.id.eventStartTextView)
        ).check(matches(withText("18:30 PM")))

        onView(
            withRecyclerView(R.id.eventsRecyclerView)
                .atPositionOnView(0, R.id.eventStartTextView)
        ).check(matches(withFontSize(12f)))

    }

    @Test
    fun checkEventItemLocationView() {
        intentTestRule.launchActivity(null)

        IdlingRegistry.getInstance().register(EventsListView.countingIdlingResource)

        onView(
            withRecyclerView(R.id.eventsRecyclerView)
                .atPositionOnView(0, R.id.eventLocationLabel)
        ).check(matches(withText("Location")))

        onView(
            withRecyclerView(R.id.eventsRecyclerView)
                .atPositionOnView(0, R.id.eventLocationLabel)
        ).check(matches(withFontSize(12f)))

        onView(
            withRecyclerView(R.id.eventsRecyclerView)
                .atPositionOnView(0, R.id.eventLocationTextView)
        ).check(matches(withText("Berlin")))

        onView(
            withRecyclerView(R.id.eventsRecyclerView)
                .atPositionOnView(0, R.id.eventLocationTextView)
        ).check(matches(withFontSize(12f)))

    }

    @Test
    fun checkEventItemDateView() {
        intentTestRule.launchActivity(null)

        IdlingRegistry.getInstance().register(EventsListView.countingIdlingResource)

        onView(
            withCustomViewOnRecyclerView(
                R.id.eventDayNumberTextView,
                R.id.weekDayView,
                R.id.eventsRecyclerView
            )
        ).check(matches(withText("5")))

        onView(
            withCustomViewOnRecyclerView(
                R.id.eventDayTextView,
                R.id.weekDayView,
                R.id.eventsRecyclerView
            )
        ).check(matches(withText("Thu")))

        onView(
            withCustomViewOnRecyclerView(
                R.id.monthTextView,
                R.id.monthView,
                R.id.eventsRecyclerView
            )
        ).check(matches(withText("Mar")))

    }

    @Test
    fun testGoToWishListView() {
        intentTestRule.launchActivity(null)

        IdlingRegistry.getInstance().register(EventsListView.countingIdlingResource)

        onView(
            withCustomViewAtElement(
                R.id.wishListMenuItem,
                R.id.toolbarView
            )
        ).perform(ViewActions.click())

        intended(hasComponent(WishListActivity::class.java.name))
    }

    @Test
    fun testErrorScreen() {
        AppStubber.stubTicketMasterResponseWithError(500)
        intentTestRule.launchActivity(null)

        IdlingRegistry.getInstance().register(EventsListView.countingIdlingResource)

        onView(ViewMatchers.withId(R.id.errorView)).check(matches(isDisplayed()))
    }

}