package ca.etsmtl.applets.etsmobile.presentation

import android.support.design.widget.TextInputLayout
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.login.WelcomeActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Sonphil on 17-04-18.
 */
@RunWith(AndroidJUnit4::class)
class WelcomeActivityTest {
    @get:Rule
    var activityTestRule = ActivityTestRule(WelcomeActivity::class.java)

    @Test
    fun testUniversalCodeDialog() {
        onView(ViewMatchers.withId(R.id.btnUniversalCodeInfo)).perform(click())

        onView(withText(R.string.info_universal_code)).check(matches(isDisplayed()))
    }

    @Test
    fun testInvalidUniversalCode() {
        val activity: WelcomeActivity = activityTestRule.activity

        onView(withId(R.id.universalCode)).perform(replaceText("AM1234"))
        onView(withId(R.id.btnSignIn)).perform(click())

        onView(withId(R.id.layoutUniversalCode)).check(matches(withError(activity.getString(R.string.error_invalid_universal_code))))
    }

    @Test
    fun testEmptyUniversalCode() {
        val activity: WelcomeActivity = activityTestRule.activity

        // Focus universal code field
        onView(withId(R.id.universalCode)).perform(click())

        // Set empty universal code
        onView(withId(R.id.universalCode)).perform(replaceText(""))

        // Remove focus from universal code field
        onView(withId(R.id.layoutPassword)).perform(click())

        // Check error
        onView(withId(R.id.layoutUniversalCode)).check(matches(withError(activity.getString(R.string.error_field_required))))
    }

    @Test
    fun testEmptyPasswordCode() {
        val activity: WelcomeActivity = activityTestRule.activity

        // Set valid universal code
        onView(withId(R.id.universalCode)).perform(replaceText("AM112345"))

        // Focus password field
        onView(withId(R.id.password)).perform(click())

        // Set empty password
        onView(withId(R.id.password)).perform(replaceText(""))

        // Remove focus from password field
        onView(withId(R.id.universalCode)).perform(click())

        onView(withId(R.id.layoutPassword)).check(matches(withError(activity.getString(R.string.error_field_required))))
    }

    private fun withError(expected: String): Matcher<Any> {
        return object : TypeSafeMatcher<Any>() {
            override fun matchesSafely(item: Any?): Boolean {
                return if (item is TextInputLayout) {
                    item.error == expected
                } else {
                    false
                }
            }

            override fun describeTo(description: Description?) {
                description?.appendText("Did not find [$expected]")
            }
        }
    }
}