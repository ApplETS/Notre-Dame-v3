package ca.etsmtl.applets.etsmobile.presentation.security

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.google.android.gms.maps.MapView

class CustomMapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MapView(context, attrs, defStyleAttr) {

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        /**
         * Request all parents to relinquish the touch events
         */
        parent.requestDisallowInterceptTouchEvent(true)
        return super.dispatchTouchEvent(ev)
    }
}