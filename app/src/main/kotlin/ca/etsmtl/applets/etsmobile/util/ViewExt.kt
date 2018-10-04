package ca.etsmtl.applets.etsmobile.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
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

/**
 * Runs an animation that rotates the view
 *
 * @param fromDegrees Rotation offset to apply at the start of the animation.
 * @param toDegrees Rotation offset to apply at the end of the animation.
 * @param pivotXType Specifies how pivotXValue should be interpreted. One of Animation.ABSOLUTE,
 * Animation.RELATIVE_TO_SELF, or Animation.RELATIVE_TO_PARENT.
 * @param pivotXValue The X coordinate of the point about which the object is being rotated,
 * specified as an absolute number where 0 is the left edge. This value can either be an absolute
 * number if pivotXType is ABSOLUTE, or a percentage (where 1.0 is 100%) otherwise.
 * @param pivotYType Specifies how pivotYValue should be interpreted. One of Animation.ABSOLUTE,
 * Animation.RELATIVE_TO_SELF, or Animation.RELATIVE_TO_PARENT.
 * @param pivotYValue The Y coordinate of the point about which the object is being rotated,
 * specified as an absolute number where 0 is the top edge. This value can either be an absolute
 * number if pivotYType is ABSOLUTE, or a percentage (where 1.0 is 100%) otherwise.
 * @param duration Amount of time (in milliseconds) for the animation to run.
 */
fun View.rotate(
    fromDegrees: Float,
    toDegrees: Float,
    pivotXType: Int = Animation.RELATIVE_TO_SELF,
    pivotXValue: Float = 0.5f,
    pivotYType: Int = Animation.RELATIVE_TO_SELF,
    pivotYValue: Float = 0.5f,
    duration: Long = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
) {
    RotateAnimation(
            fromDegrees,
            toDegrees,
            pivotXType,
            pivotXValue,
            pivotYType,
            pivotYValue
    ).apply {
        this.duration = duration
        fillAfter = true
        startAnimation(this)
    }
}