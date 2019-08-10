package ca.etsmtl.applets.etsmobile.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import ca.etsmtl.applets.etsmobile.util.ProgressTimeLatch

/**
 * A [ProgressBar] combined with a [ProgressTimeLatch] to prevent it from "flashing". The
 * [ProgressTimeLatch] waits a minimum of time before showing the [ProgressBar] and, once, visible,
 * the [ProgressBar] will be visible for a minimum of time.
 *
 * Created by Sonphil on 09-08-19.
 */

class ProgressBarWithProgressTimeLatch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ProgressBar(context, attrs) {
    private val progressLatch: ProgressTimeLatch by lazy {
        ProgressTimeLatch(300, 1500) { visible ->
            val visibility = if (visible) View.VISIBLE else View.GONE

            super.setVisibility(visibility)
        }
    }

    override fun setVisibility(visibility: Int) {
        progressLatch.refreshing = visibility == View.VISIBLE
    }
}