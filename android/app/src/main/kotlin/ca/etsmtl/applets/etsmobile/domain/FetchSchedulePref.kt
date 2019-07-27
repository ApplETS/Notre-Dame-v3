package ca.etsmtl.applets.etsmobile.domain

import android.content.Context
import android.content.SharedPreferences
import ca.etsmtl.applets.etsmobile.R
import javax.inject.Inject

/**
 * Created by Sonphil on 26-07-19.
 */

class FetchSchedulePref @Inject constructor(
    private val context: Context,
    private val prefs: SharedPreferences
) {
    fun showTodayButton() = context.run {
        prefs.getBoolean(
            getString(R.string.key_schedule_show_today_btn_pref),
            resources.getBoolean(R.bool.default_schedule_show_today_btn_pref)
        )
    }

    fun numberOfVisibleDays() = context.run {
        prefs.getInt(
            getString(R.string.key_schedule_no_visible_days_pref),
            resources.getInteger(R.integer.default_schedule_no_visible_days_pref)
        )
    }

    fun xScrollingSpeed() = context.run {
        prefs.getInt(
            getString(R.string.key_schedule_x_scrolling_speed_pref),
            resources.getInteger(R.integer.default_schedule_x_scrolling_speed_pref)
        ).toFloat()
    }

    fun scrollDuration() = context.run {
        prefs.getInt(
            getString(R.string.key_schedule_scroll_duration_pref),
            resources.getInteger(R.integer.default_schedule_scroll_duration_pref)
        )
    }
}