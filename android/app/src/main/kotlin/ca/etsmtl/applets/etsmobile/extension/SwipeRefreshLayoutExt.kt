package ca.etsmtl.applets.etsmobile.extension

import android.content.Context
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ca.etsmtl.applets.etsmobile.R

/**
 * Created by Sonphil on 08-05-19.
 */
 
fun SwipeRefreshLayout.applyAppTheme(context: Context) {
    setProgressBackgroundColorSchemeColor(context.getColorCompat(R.color.colorSwipeRefreshBg))
    setColorSchemeResources(R.color.colorPrimary)
}