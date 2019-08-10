package ca.etsmtl.applets.etsmobile.view

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ca.etsmtl.applets.etsmobile.util.ProgressTimeLatch

/**
 * A [SwipeRefreshLayout] combined with a [ProgressTimeLatch] to prevent it from "flashing". The
 * [ProgressTimeLatch] waits a minimum of time before showing the [SwipeRefreshLayout] and, once,
 * visible, the [SwipeRefreshLayout] will be visible for a minimum of time.
 *
 * Created by Sonphil on 30-03-19.
 */

class SwipeRefreshLayoutWithProgressTimeLatch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SwipeRefreshLayout(context, attrs) {
    private val swipeRefreshLatch: ProgressTimeLatch by lazy {
        ProgressTimeLatch(200, 1500) {
            super.setRefreshing(it)
        }
    }

    override fun setRefreshing(refreshing: Boolean) {
        swipeRefreshLatch.refreshing = refreshing
    }
}