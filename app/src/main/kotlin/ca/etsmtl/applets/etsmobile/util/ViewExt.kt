package ca.etsmtl.applets.etsmobile.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by Sonphil on 23-06-18.
 */

/**
 * Fades a [View] to the provided visibility state
 *
 * @param visibility [View.VISIBLE], [View.GONE] or [View.INVISIBLE]
 * @param animTime Fade duration
 */
fun View.fadeTo(
    visibility: Int,
    animTime: Long = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
) {
    animate().setDuration(animTime)
            .alpha((if (visibility == View.VISIBLE) 1f else 0f))
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    this@fadeTo.visibility = visibility
                }
            })
}

/**
 * Shows or hides the view
 *
 * @param show True if the view should be shown or false if the view should be gone
 */
fun View.show(show: Boolean) {
    visibility = when {
        show -> View.VISIBLE
        else -> View.GONE
    }
}

/**
 * Tries to hide the keyboard
 *
 * @return True if it worked
 */
fun View.hideKeyboard(): Boolean {
    return try {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
        false
    }
}