package com.ddworks.nytimesmostpopular

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.ddworks.nytimesmostpopular.util.NewsAdapter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

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
    fun openList(){
        onView(withId(R.id.rv_items)).check(matches(isDisplayed()))
    }

    @Test
    fun openNewsDetails(){
        onView(withId(R.id.rv_items)).perform(actionOnItemAtPosition<NewsAdapter.NewsViewHolder>(0, MyViewAction.clickOnViewChild(R.id.iv_opendetailactivity)))
        onView(withId(R.id.detailsFragment)).check(matches(isDisplayed()))
    }

    @Test
    fun searchInTheList(){
        //TODO Test searching in rv list
    }

    object MyViewAction {
        fun clickOnViewChild(viewId: Int) = object : ViewAction {
            override fun getConstraints() = null

            override fun getDescription() = "Click on a child view with specified id."

            override fun perform(uiController: UiController, view: View) = click().perform(uiController, view.findViewById<View>(viewId))
        }
    }
}