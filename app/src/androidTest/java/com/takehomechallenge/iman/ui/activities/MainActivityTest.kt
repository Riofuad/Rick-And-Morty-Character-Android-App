package com.takehomechallenge.iman.ui.activities

import android.view.KeyEvent
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.takehomechallenge.iman.di.EspressoIdling
import com.takehomechallenge.iman.R
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.JVM)
@SmallTest
class MainActivityTest {

    @Rule
    @JvmField
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdling.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdling.countingIdlingResource)
    }

    @Test
    fun addFavoriteCharacter() {
        Thread.sleep(2000)
        onView(withId(R.id.rv_character)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.fab_favorite)).perform(click())
        pressBack()
        onView(withId(R.id.menu_favorite)).perform(click())
    }

    @Test
    fun deleteFavoriteCharacterIfExist() {
        Thread.sleep(2000)
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(withId(R.id.rv_favorite_character)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.fab_favorite)).perform(click())
        pressBack()
        pressBack()
    }

    @Test
    fun serachCharacter() {
        Thread.sleep(2000)
        onView(withId(R.id.menu_search)).perform(click())
        onView(isAssignableFrom(SearchView::class.java)).check(matches(isDisplayed()))
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("Morty"))
        onView(isAssignableFrom(EditText::class.java)).perform(pressKey(KeyEvent.KEYCODE_ENTER))
        Thread.sleep(2000)
        onView(withId(R.id.rv_character)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                3,
                click()
            )
        )
        pressBack()
    }
}