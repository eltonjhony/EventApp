package com.eltonjhony.eventapp.utils

import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher

object ViewAssertions {

    fun withFontSize(expectedSize: Float): Matcher<View> {
        return object : BoundedMatcher<View, View>(View::class.java) {

            override fun matchesSafely(target: View): Boolean {
                if (target !is TextView) {
                    return false
                }
                val pixels = target.textSize
                val actualSize = pixels / target.getResources().displayMetrics.scaledDensity
                return actualSize.compareTo(expectedSize) == 0
            }

            override fun describeTo(description: Description) {
                description.appendText("with fontSize: ")
                description.appendValue(expectedSize)
            }
        }
    }

    fun withCustomViewOnRecyclerView(
        @IdRes customViewElementId: Int,
        @IdRes customViewId: Int,
        @IdRes recyclerViewId: Int,
        position: Int = 0
    ): Matcher<View>? {
        return CoreMatchers.allOf(
            withId(customViewElementId),
            ViewMatchers.isDescendantOfA(
                RecyclerViewMatcher.withRecyclerView(recyclerViewId)
                    .atPositionOnView(position, customViewId)
            )
        )
    }

    fun withCustomViewAtElement(
        @IdRes customViewElementId: Int,
        @IdRes customViewId: Int
    ): Matcher<View>? {
        return CoreMatchers.allOf(
            withId(customViewElementId),
            ViewMatchers.isDescendantOfA(
                withId(customViewId)
            )
        )
    }

}