package ca.etsmtl.applets.etsmobile.presentation.whatsnew

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import ca.etsmtl.applets.etsmobile.R
import kotlinx.android.synthetic.main.fragment_whats_new.*
import java.util.*

class WhatsNewFragment : Fragment() {
    private var mWhatsNewData = ArrayList<WhatsNewObject>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_whats_new, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setViewListener()
    }

    private fun setViewListener() {
        btnCloseWhatsNew.setOnClickListener {
            TODO()
        }
    }

    private fun setRecyclerView() {

        rvWhatsNew.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = WhatsNewAdapter(mWhatsNewData)
            addItemDecoration(CirclePagerIndicatorDecoration())
        }
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rvWhatsNew)
        initializeData()
    }

    private fun initializeData() {
        // Get the resources from the XML file.
        val title = arrayOf("Whats1", "Whats2", "Whats3")
        val desc = arrayOf("changes1", "changes2", "changes3")
        val version = arrayOf("123.21", "122134", "234.324.436")
        // Clear the existing data (to avoid duplication).
        mWhatsNewData.clear()

        // Create the ArrayList of Sports objects with titles and
        // information about each sport.
        for (i in title.indices) {
            mWhatsNewData.add(WhatsNewObject(title[i], desc[i], version[i]))
        }

        // Notify the adapter of the change.
        rvWhatsNew!!.adapter?.notifyDataSetChanged()
    }

    class CirclePagerIndicatorDecoration : RecyclerView.ItemDecoration() {
        /**
         * Height of the space the indicator takes up at the bottom of the view.
         */
        private val mIndicatorHeight = (DP * 16).toInt()

        /**
         * Indicator stroke width.
         */
        private val mIndicatorStrokeWidth = DP * 2

        /**
         * Indicator width.
         */
        private val mIndicatorItemLength = DP * 16

        /**
         * Padding between indicators.
         */
        private val mIndicatorItemPadding = DP * 6

        /**
         * Some more natural animation interpolation
         */
        private val mInterpolator: Interpolator = AccelerateDecelerateInterpolator()
        private val mPaint: Paint = Paint()
        override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            super.onDrawOver(c, parent, state)
            val itemCount = parent.adapter!!.itemCount

            // center horizontally, calculate width and subtract half from center
            val totalLength = mIndicatorItemLength * itemCount
            val paddingBetweenItems = kotlin.math.max(0, itemCount - 1) * mIndicatorItemPadding
            val indicatorTotalWidth = totalLength + paddingBetweenItems
            val indicatorStartX = (parent.width - indicatorTotalWidth) / 2f

            // center vertically in the allotted space
            val indicatorPosY = parent.height - mIndicatorHeight / 2f
            drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount)

            // find active page (which should be highlighted)
            val layoutManager = parent.layoutManager as LinearLayoutManager?
            val activePosition = layoutManager!!.findFirstVisibleItemPosition()
            if (activePosition == RecyclerView.NO_POSITION) {
                return
            }

            // find offset of active page (if the user is scrolling)
            val activeChild = layoutManager.findViewByPosition(activePosition)
            val left = activeChild!!.left
            val width = activeChild.width

            // on swipe the active item will be positioned from [-width, 0]
            // interpolate offset for smooth animation
            val progress: Float = mInterpolator.getInterpolation(left * -1 / width.toFloat())
            drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress)
        }

        private fun drawInactiveIndicators(c: Canvas, indicatorStartX: Float, indicatorPosY: Float, itemCount: Int) {
            mPaint.color = Color.GRAY

            // width of item indicator including padding
            val itemWidth = mIndicatorItemLength + mIndicatorItemPadding
            var start = indicatorStartX
            for (i in 0 until itemCount) {
                // draw the line for every item
                c.drawCircle(start + mIndicatorItemLength, indicatorPosY, itemWidth / 6, mPaint)
                //  c.drawLine(start, indicatorPosY, start + mIndicatorItemLength, indicatorPosY, mPaint);
                start += itemWidth
            }
        }

        private fun drawHighlights(c: Canvas, indicatorStartX: Float, indicatorPosY: Float, highlightPosition: Int, progress: Float) {
            mPaint.color = Color.WHITE
            // width of item indicator including padding
            val itemWidth = mIndicatorItemLength + mIndicatorItemPadding
            if (progress == 0f) {
                // no swipe, draw a normal indicator
                val highlightStart = indicatorStartX + itemWidth * highlightPosition
                c.drawCircle(highlightStart, indicatorPosY, itemWidth / 6, mPaint)
            } else {
                val highlightStart = indicatorStartX + itemWidth * highlightPosition
                c.drawCircle(highlightStart + mIndicatorItemLength, indicatorPosY, itemWidth / 6, mPaint)
            }
        }

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.bottom = mIndicatorHeight
        }

        companion object {
            private val DP: Float = android.content.res.Resources.getSystem().displayMetrics.density
        }

        init {
            mPaint.strokeCap = Paint.Cap.ROUND
            mPaint.strokeWidth = mIndicatorStrokeWidth
            mPaint.style = Paint.Style.FILL
            mPaint.isAntiAlias = true
        }
    }
}