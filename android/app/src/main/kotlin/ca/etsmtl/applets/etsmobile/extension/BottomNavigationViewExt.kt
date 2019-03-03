package ca.etsmtl.applets.etsmobile.extension

import android.animation.Animator
import androidx.core.view.isVisible
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Created by Sonphil on 30-12-18.
 *
 */

fun BottomNavigationView.toggle(show: Boolean, duration: Long = 200) {
    clearAnimation()
    animate()
        .translationY(when (show) {
            true -> 0f
            false -> height.toFloat()
        })
        .setDuration(duration)
        .setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                if (!show) { // Need to hide view
                    // Set visibility to GONE at the end of animation
                    isVisible = false
                }
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationStart(animator: Animator) {
                if (show) { // Need to reveal view
                    // Set visibility to VISIBLE at the beginning of animation
                    isVisible = true
                }
            }
        })
}