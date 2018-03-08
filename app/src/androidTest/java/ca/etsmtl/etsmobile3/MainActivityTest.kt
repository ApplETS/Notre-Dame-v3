package ca.etsmtl.etsmobile3

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import ca.etsmtl.etsmobile3.presentation.MainActivity
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



/**
 * Created by Sonphil on 24-02-18.
 */

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testNavigation() {
        val activity: MainActivity = activityTestRule.activity

        testTitles(activity)
    }

    private fun testTitles(activity: MainActivity) {
        assertTrue(activity.title == activity.getString(R.string.title_home))

        onView(withId(R.id.navigation_schedule)).perform(click())
        assertTrue(activity.title == activity.getString(R.string.title_schedule))

        onView(withId(R.id.navigation_profile)).perform(click())
        assertTrue(activity.title == activity.getString(R.string.title_profile))

        onView(withId(R.id.navigation_ets)).perform(click())
        assertTrue(activity.title == activity.getString(R.string.title_ets))

        onView(withId(R.id.navigation_more)).perform(click())
        assertTrue(activity.title == activity.getString(R.string.title_more))

        onView(withId(R.id.navigation_home)).perform(click())
        assertTrue(activity.title == activity.getString(R.string.title_home))
    }

    @Test
    fun testBack() {
        val activity: MainActivity = activityTestRule.activity

        assertTrue(activity.title == activity.getString(R.string.title_home))

        onView(withId(R.id.navigation_profile)).perform(click())
        assertTrue(activity.title == activity.getString(R.string.title_profile))

        Espresso.pressBack()

        assertTrue(activity.title == activity.getString(R.string.title_home))
    }

    @Test
    fun testFail() {
        fail()
    }
}