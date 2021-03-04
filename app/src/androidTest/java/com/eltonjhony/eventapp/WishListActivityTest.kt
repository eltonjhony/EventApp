package com.eltonjhony.eventapp

import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.eltonjhony.eventapp.mock.AppStubber
import com.eltonjhony.eventapp.presentation.features.listevents.ListEventsActivity
import com.eltonjhony.eventapp.presentation.views.EventsListView
import com.eltonjhony.eventapp.utils.ViewAssertions.withCustomViewAtElement
import com.eltonjhony.eventapp.utils.ViewAssertions.withCustomViewOnRecyclerView
import com.github.tomakehurst.wiremock.common.ConsoleNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class WishListActivityTest {

    @get:Rule
    var intentTestRule =
        IntentsTestRule(ListEventsActivity::class.java, true, false)

    @get:Rule
    var wireMockRule = WireMockRule(
        WireMockConfiguration.wireMockConfig()
            .port(8080)
            .notifier(ConsoleNotifier(true))
    )

    @Before
    fun setup() {
        AppStubber.stubTicketMasterResponseWithSuccess("ticketmaster_response.json")
    }

    @Test
    fun testWishListSimpleBehavior() {
        intentTestRule.launchActivity(null)

        IdlingRegistry.getInstance().register(EventsListView.countingIdlingResource)

        onView(
            withCustomViewOnRecyclerView(
                R.id.favoriteView,
                R.id.eventItemView,
                R.id.eventsRecyclerView
            )
        ).perform(ViewActions.click())

        onView(
            allOf(
                withId(R.id.wishListNumber),
                isDescendantOfA(
                    withCustomViewAtElement(
                        R.id.wishListMenuItem,
                        R.id.toolbarView
                    )
                )
            )
        ).check(matches(withText("1")))

        onView(
            withCustomViewAtElement(
                R.id.wishListMenuItem,
                R.id.toolbarView
            )
        ).perform(ViewActions.click())

        onView(
            withCustomViewOnRecyclerView(
                R.id.favoriteView,
                R.id.eventItemView,
                R.id.eventsRecyclerView
            )
        ).perform(ViewActions.click())

    }

    companion object {
        @BeforeClass
        @JvmStatic
        fun setupClass() {
            getTargetContext().deleteDatabase("events.db")
        }
    }

}