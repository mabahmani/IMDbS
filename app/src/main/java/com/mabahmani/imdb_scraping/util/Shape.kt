package com.mabahmani.imdb_scraping.util

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable

object Shape {


    fun createStateDrawable(colors: IntArray, drawable: Drawable?): Drawable? {

        if (drawable != null){
            val stateEnableDrawable = createColorFilterDrawable(colors[0], drawable)
            val stateEnablePressedDrawable = createColorFilterDrawable(colors[1], drawable)
            val stateDisableDrawable = createColorFilterDrawable(colors[2], drawable)


            val stateListDrawable = StateListDrawable()
            //stateListDrawable.setEnterFadeDuration(200)
            stateListDrawable.setExitFadeDuration(200)
            stateListDrawable.addState(intArrayOf(android.R.attr.state_enabled, -android.R.attr.state_pressed),stateEnableDrawable)
            stateListDrawable.addState(intArrayOf(android.R.attr.state_enabled, android.R.attr.state_pressed),stateEnablePressedDrawable)
            stateListDrawable.addState(intArrayOf(- android.R.attr.state_enabled),stateDisableDrawable)

            return stateListDrawable
        }

        return null
    }

    fun createColorStateDrawable(colors: IntArray, drawable: Drawable?): Drawable? {

        if (drawable != null){
            val stateEnableDrawable = createColorFilterDrawable(colors[0], drawable)
            val stateEnablePressedDrawable = createColorFilterDrawable(colors[1], drawable)
            val stateDisableDrawable = createColorFilterDrawable(colors[2], drawable)


            val stateListDrawable = StateListDrawable()
            //stateListDrawable.setEnterFadeDuration(200)
            stateListDrawable.setExitFadeDuration(200)
            stateListDrawable.addState(intArrayOf(android.R.attr.state_enabled, -android.R.attr.state_pressed),stateEnableDrawable)
            stateListDrawable.addState(intArrayOf(android.R.attr.state_enabled, android.R.attr.state_pressed),stateEnablePressedDrawable)
            stateListDrawable.addState(intArrayOf(- android.R.attr.state_enabled),stateDisableDrawable)

            return stateListDrawable
        }

        return null
    }


    fun createRectangleWithCustomRadiusShape(color: Int, radiusTopRight:Float, radiusTopLeft:Float, radiusBottomLeft:Float, radiusBottomRight:Float): GradientDrawable{
        val gradientDrawable =  GradientDrawable()
        gradientDrawable.shape = GradientDrawable.RECTANGLE
        gradientDrawable.setColor(color)
        gradientDrawable.cornerRadii = floatArrayOf(radiusTopLeft,radiusTopLeft,radiusTopRight,radiusTopRight,radiusBottomRight,radiusBottomRight,radiusBottomLeft,radiusBottomLeft)

        return gradientDrawable
    }

    fun createGradientDrawable(startColor: Int, centerColor: Int?, endColor: Int): GradientDrawable{
        return if (centerColor != null){
            GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(startColor, centerColor, endColor))
        } else{
            GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(startColor, endColor))
        }
    }

    private fun createColorFilterDrawable(color: Int, drawable: Drawable): Drawable? {
        val d = drawable.constantState?.newDrawable()?.mutate()
        return d?.apply {
            colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    private fun createContainedRectangleWithRadiusShape(color: Int, radius:Float): GradientDrawable{
        val gradientDrawable =  GradientDrawable()
        gradientDrawable.shape = GradientDrawable.RECTANGLE
        gradientDrawable.setColor(color)
        gradientDrawable.cornerRadius = radius

        return gradientDrawable
    }
}
