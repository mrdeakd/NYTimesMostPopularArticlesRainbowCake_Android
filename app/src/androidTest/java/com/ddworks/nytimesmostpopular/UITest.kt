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
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.ddworks.nytimesmostpopular.util.NewsAdapter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.ddworks.nytimesmostpopular.domain.DomainNews
import com.ddworks.nytimesmostpopular.util.FirebaseHelper
import org.junit.Assert


@RunWith(AndroidJUnit4::class)
class UITest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, false, true)

    @Before
    fun setup() {
        val idlingResource = (activityRule.activity as MainActivity).getidlingResource()
        IdlingRegistry.getInstance().register(idlingResource)

        if(!FirebaseHelper.userIsLoggedIn()){
            onView(withId(R.id.loginEmailAddress)).perform(typeText("teszt1@gmail.com"))
            Espresso.pressBack()
            onView(withId(R.id.loginPassword)).perform(typeText("123456"))
            Espresso.pressBack()
            onView(withId(R.id.buttonLogin)).perform(click())
        }
    }

    fun logOut(){
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        onView(withText("Sign Out")).perform(click())
    }

    @Test
    fun registerUserAndLogInWithThatNewUser(){
        logOut()
        val randomString = generateRandomString()
        onView(withId(R.id.tvRegister)).perform(click())
        onView(withId(R.id.registerName)).perform(typeText(randomString))
        Espresso.pressBack()
        onView(withId(R.id.registerEmailAddress)).perform(typeText("$randomString@$randomString.com"))
        Espresso.pressBack()
        onView(withId(R.id.registerPassword)).perform(typeText(randomString))
        Espresso.pressBack()
        onView(withId(R.id.buttonRegister)).perform(click())
        onView(withId(R.id.loginPassword)).perform(typeText(randomString))
        Espresso.pressBack()
        onView(withId(R.id.buttonLogin)).perform(click())
        logOut()
    }

    @Test
    fun registerUserAndLogInWithThatNewUserButWrongPassword(){
        logOut()
        val randomString = generateRandomString()
        onView(withId(R.id.tvRegister)).perform(click())
        onView(withId(R.id.registerName)).perform(typeText(randomString))
        Espresso.pressBack()
        onView(withId(R.id.registerEmailAddress)).perform(typeText("$randomString@$randomString.com"))
        Espresso.pressBack()
        onView(withId(R.id.registerPassword)).perform(typeText(randomString))
        Espresso.pressBack()
        onView(withId(R.id.buttonRegister)).perform(click())
        onView(withId(R.id.loginPassword)).perform(typeText("$randomString)"))
        Espresso.pressBack()
        onView(withId(R.id.buttonLogin)).perform(click())
        onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(withText(R.string.login_failed)))
    }

    @Test
    fun registerUseTwice(){
        logOut()
        val randomString = generateRandomString()
        onView(withId(R.id.tvRegister)).perform(click())
        onView(withId(R.id.registerName)).perform(typeText(randomString))
        Espresso.pressBack()
        onView(withId(R.id.registerEmailAddress)).perform(typeText("$randomString@$randomString.com"))
        Espresso.pressBack()
        onView(withId(R.id.registerPassword)).perform(typeText(randomString))
        Espresso.pressBack()
        onView(withId(R.id.buttonRegister)).perform(click())
        onView(withId(R.id.tvRegister)).perform(click())
        onView(withId(R.id.registerName)).perform(typeText(randomString))
        Espresso.pressBack()
        onView(withId(R.id.registerEmailAddress)).perform(typeText("$randomString@$randomString.com"))
        Espresso.pressBack()
        onView(withId(R.id.registerPassword)).perform(typeText(randomString))
        Espresso.pressBack()
        onView(withId(R.id.buttonRegister)).perform(click())
        onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(withText(R.string.registration_failed)))
    }

    fun generateRandomString(): String{
        return "ABCDEFG0HIJKLMNOPQRSTUVWXYZab1cdefghijklmnopqrstuvwxyz".map { it }.shuffled().subList(0, 6).joinToString("")
    }

    @Test
    fun openMainFragmentListAndCheckIfThatsWhatShowsOnTheDisplay(){
        onView(withId(R.id.rvItems)).check(matches(isDisplayed()))
    }

    @Test
    fun openTheFirstItemOfTheListAndCheckIfTheDetailFragmentIsOpened(){
        onView(withId(R.id.rvItems)).perform(actionOnItemAtPosition<NewsAdapter.NewsViewHolder>(0, MyViewAction.clickOnViewChild(R.id.ivOpenDetailActivity)))
        onView(withId(R.id.detailsFragment)).check(matches(isDisplayed()))
        Espresso.pressBack()
    }

    @Test
    fun searchInTheListAndCheckIfItemCountIsNotTheOriginTwenty(){
        onView(withId(R.id.action_search)).perform(click())

        val searchString = "Hello"
        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.rvItems)
        val adapter = recyclerView.adapter
        val expectedCount = countFilteredNews((adapter as NewsAdapter).getListOfNews(), searchString)

        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText(searchString))

        Assert.assertEquals(adapter.itemCount, expectedCount)

        logOut()
    }

    object MyViewAction {
        fun clickOnViewChild(viewId: Int) = object : ViewAction {
            override fun getConstraints() = null

            override fun getDescription() = "Click on a child view with specified id."

            override fun perform(uiController: UiController, view: View) = click().perform(uiController, view.findViewById<View>(viewId))
        }
    }

    private fun countFilteredNews(list: List<DomainNews>, searchString: String): Int {
        var count = 0
        list.forEach {
            if (it.title.contains(searchString)) {
                count++
            }
        }
        return count
    }
}