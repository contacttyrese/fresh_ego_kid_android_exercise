package com.example.freshegokidproject.view

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.rules.activityScenarioRule
import com.example.freshegokidproject.R
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun navigationBarAndBannerAreDisplayed() {
        onView(withId(R.id.homeTopNavigationToolbar))
            .check(matches(isDisplayed()))

        onView(withId(R.id.homeTopNavigationHomeButton))
            .check(matches(isDisplayed()))

        onView(withId(R.id.homeTopNavigationSearchButton))
            .check(matches(isDisplayed()))

        onView(withId(R.id.homeDiscountBanner))
            .check(matches(isDisplayed()))

        onView(withId(R.id.homeHeading))
            .check(matches(isDisplayed()))
    }

    @Test
    fun homeButtonKeepsUserOnHome() {
        onView(withId(R.id.homeTopNavigationHomeButton))
            .perform(click())

        onView(withId(R.id.homeDiscountBanner))
            .check(matches(isDisplayed()))
    }

    @Test
    fun searchButtonTakesUserToSearch() {
        onView(withId(R.id.homeTopNavigationSearchButton))
            .perform(click())

        onView(withId(R.id.searchScrollView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun tabItemIsDisplayedOnRecyclerView() {
        var recyclerView: RecyclerView? = null
        var itemCount: Int = 0
        scenarioRule.scenario.onActivity { activity ->
            recyclerView = activity.findViewById<RecyclerView>(R.id.homeRecyclerView)
        }
        recyclerView?.let { recycler ->
            recycler.adapter?.let { adapter ->
                itemCount = adapter.itemCount
            }
        }

        if (itemCount > 0) {
            onView(withId(R.id.itemview_productprice))
                .check(matches(isDisplayed()))
        }
    }

    @Test
    fun tabItemNavigatesToDetails() {
        var recyclerView: RecyclerView? = null
        var itemCount: Int = 0
        scenarioRule.scenario.onActivity { activity ->
            recyclerView = activity.findViewById<RecyclerView>(R.id.homeRecyclerView)
        }
        recyclerView?.let { recycler ->
            recycler.adapter?.let { adapter ->
                itemCount = adapter.itemCount
            }
        }

        if (itemCount > 0) {
            onView(withId(R.id.itemview_productprice))
                .perform(click())
        }

        onView(withId(R.id.topNavigationToolbarDetail))
            .check(matches(isDisplayed()))
    }
}