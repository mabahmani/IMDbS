package com.mabahmani.imdb_scraping.ui.custom

import android.content.res.Resources
import android.graphics.*
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.imdb_scraping.util.dp

class RoundedPagerIndicatorDecoration : RecyclerView.ItemDecoration() {

    private val colorActive = Color.parseColor("#0C4A6E")
    private val colorInactive = Color.parseColor("#CBD5E1")

    /**
     * Height of the space the indicator takes up at the bottom of the view.
     */
    private val mIndicatorHeight = 32.dp()

    /**
     * Indicator stroke width.
     */
    private val mIndicatorStrokeWidth = 1f.dp()

    /**
     * Indicator width.
     */
    private val mIndicatorItemLength = 8.dp()
    /**
     * Padding between indicators.
     */
    private val mIndicatorItemPadding = 4.dp()

    /**
     * Some more natural animation interpolation
     */
    private val mInterpolator = AccelerateDecelerateInterpolator()

    private val mPaint = Paint()

    init {
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeWidth = mIndicatorStrokeWidth
        mPaint.isAntiAlias = true
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val itemCount = parent.adapter!!.itemCount

        // center horizontally, calculate width and subtract half from center
        val totalLength = mIndicatorItemLength * itemCount
        val paddingBetweenItems = 0.coerceAtLeast(itemCount - 1) * mIndicatorItemPadding
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
        val progress = mInterpolator.getInterpolation(left * -1 / width.toFloat())

        drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress, itemCount)
    }

    private fun drawInactiveIndicators(c: Canvas, indicatorStartX: Float, indicatorPosY: Float, itemCount: Int) {
        mPaint.color = colorInactive
        mPaint.style = Paint.Style.STROKE

        // width of item indicator including padding
        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding

        var start = indicatorStartX
        for (i in 0 until itemCount) {
            // draw the line for every item
            //c.drawLine(start, indicatorPosY, start + mIndicatorItemLength, indicatorPosY, mPaint)
                c.drawRoundRect(RectF(start, indicatorPosY, start + mIndicatorItemLength, indicatorPosY + mIndicatorItemLength),2f.dp(),2f.dp(), mPaint)
            start += itemWidth
        }
    }

    private fun drawHighlights(
        c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
        highlightPosition: Int, progress: Float, itemCount: Int
    ) {
        mPaint.color = colorActive
        mPaint.style = Paint.Style.FILL

        // width of item indicator including padding
        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding

        if (progress == 0f) {
            // no swipe, draw a normal indicator
            val highlightStart = indicatorStartX + itemWidth * highlightPosition

//            c.drawLine(
//                highlightStart, indicatorPosY,
//                highlightStart + mIndicatorItemLength, indicatorPosY, mPaint
//            )

            c.drawRoundRect(RectF(highlightStart, indicatorPosY, highlightStart + mIndicatorItemLength, indicatorPosY + mIndicatorItemLength),2f.dp(),2f.dp(), mPaint)


        } else {
            var highlightStart = indicatorStartX + itemWidth * highlightPosition
            // calculate partial highlight
            val partialLength = mIndicatorItemLength * progress

            // draw the cut off highlight

//            c.drawLine(
//                highlightStart + partialLength, indicatorPosY,
//                highlightStart + mIndicatorItemLength, indicatorPosY, mPaint
//            )

            c.drawRoundRect(RectF(highlightStart + partialLength, indicatorPosY, highlightStart + mIndicatorItemLength, indicatorPosY + mIndicatorItemLength),2f.dp(),2f.dp(), mPaint)


            // draw the highlight overlapping to the next item as well
            if (highlightPosition < itemCount - 1) {
                highlightStart += itemWidth

//                c.drawLine(
//                    highlightStart, indicatorPosY,
//                    highlightStart + partialLength, indicatorPosY, mPaint
//                )

                c.drawRoundRect(RectF(highlightStart, indicatorPosY, highlightStart + partialLength, indicatorPosY + mIndicatorItemLength),2f.dp(),2f.dp(), mPaint)

            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = mIndicatorHeight
    }

}