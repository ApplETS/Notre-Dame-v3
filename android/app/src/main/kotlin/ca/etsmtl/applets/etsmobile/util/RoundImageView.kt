package ca.etsmtl.applets.etsmobile.util

import android.content.Context
import android.graphics.Outline
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.appcompat.widget.AppCompatImageView

/**
 * Created by Sonphil on 13-10-18.
 */

class RoundImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    init {
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                with(view.bounds) {
                    outline.setRoundRect(this, width() / 2f)
                }
            }
        }

        clipToOutline = true
    }
}

private val View.bounds: Rect
    get() {
        val widthWithoutPadding = width - paddingLeft - paddingRight
        val heightWithoutPadding = height - paddingTop - paddingBottom

        /** Diameter of the circular view **/
        val diameter = Math.min(widthWithoutPadding, heightWithoutPadding)

        /** The distance between x = 0 and the left side **/
        val left = paddingLeft + ((widthWithoutPadding - diameter) / 2)

        /** The distance between x = 0 and the right side **/
        val right = left + diameter

        /** The distance between y = 0 and the top side **/
        val top = paddingTop + ((heightWithoutPadding - diameter) / 2)

        /** The distance between y = 0 and the bottom side **/
        val bottom = top + diameter

        return Rect(left, top, right, bottom)
    }