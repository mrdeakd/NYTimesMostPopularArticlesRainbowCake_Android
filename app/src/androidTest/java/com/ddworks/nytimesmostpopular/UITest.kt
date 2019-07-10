package com.ddworks.nytimesmostpopular

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.ddworks.nytimesmostpopular.util.NewsAdapter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertNotEquals


@RunWith(AndroidJUnit4::class)
class UITest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, false, true)

    @Before
    fun setup() {
        val idlingResource = (activityRule.activity as MainActivity).getidlingResource()
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @Test
    fun `open main Fragment list and check if thats what shows on the display`(){
        onView(withId(R.id.rv_items)).check(matches(isDisplayed()))
    }

    @Test
    fun `open the first item of the list and check if the detailFragment is opened`(){
        onView(withId(R.id.rv_items)).perform(actionOnItemAtPosition<NewsAdapter.NewsViewHolder>(0, MyViewAction.clickOnViewChild(R.id.iv_opendetailactivity)))
        onView(withId(R.id.detailsFragment)).check(matches(isDisplayed()))
    }

    @Test
    fun `search in the list and check if itemCount is not the origin twenty`(){
        onView(withId(R.id.action_search)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("Random text"))
        onView(withId(androidx.appcompat.R.id.search_src_text)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_items)).check(RecyclerViewItemCountAssertion(20))
    }

    object MyViewAction {
        fun clickOnViewChild(viewId: Int) = object : ViewAction {
            override fun getConstraints() = null

            override fun getDescription() = "Click on a child view with specified id."

            override fun perform(uiController: UiController, view: View) = click().perform(uiController, view.findViewById<View>(viewId))
        }
    }

    inner class RecyclerViewItemCountAssertion(private val expectedCount: Int) : ViewAssertion {
        override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            val adapter = recyclerView.adapter
            assertNotEquals(adapter!!.itemCount, `is`(expectedCount))
        }
    }
}