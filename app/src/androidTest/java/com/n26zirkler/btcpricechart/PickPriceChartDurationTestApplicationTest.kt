package com.n26zirkler.btcpricechart

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.n26zirkler.btcpricechart.presentation.MainActivity
import org.junit.Test

class PickPriceChartDurationTestApplicationTest {
    @Test
    fun run() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.radio_all)).perform(click())
        onView(withId(R.id.lblValue)).check(matches(withText("4,000,000.00 USD")))

        // We are expecting an error on the 5 weeks timespan, as it implemented in the FakePriceDataSource
        onView(withId(R.id.radio_five_weeks)).perform(click())
        onView(withId(R.id.llErrorContainer)).check(matches(isDisplayed()))
        onView(withId(R.id.lineChart)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))

        // We are expecting the error to be gone.
        onView(withId(R.id.radio_1_year)).perform(click())
        onView(withId(R.id.llErrorContainer)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
        onView(withId(R.id.lineChart)).check(matches(isDisplayed()))
        onView(withId(R.id.lblValue)).check(matches(withText("66,612.50 USD")))

        onView(withId(R.id.radio_6_months)).perform(click())
        onView(withId(R.id.lblValue)).check(matches(withText("1,656.20 USD")))
    }
}
