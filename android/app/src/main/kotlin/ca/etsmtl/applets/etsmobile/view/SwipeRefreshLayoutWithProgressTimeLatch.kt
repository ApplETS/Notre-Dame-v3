package ca.etsmtl.applets.etsmobile.view

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ca.etsmtl.applets.etsmobile.util.ProgressTimeLatch

/**
 * Created by Sonphil on 30-03-19.
 */

class SwipeRefreshLayoutWithProgressTimeLatch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SwipeRefreshLayout(context, attrs) {
    private val swipeRefreshLatch: ProgressTimeLatch by lazy {
        ProgressTimeLatch {
            super.setRefreshing(it)
        }
    }

    override fun setRefreshing(refreshing: Boolean) {
        swipeRefreshLatch.refreshing = refreshing
    }
}