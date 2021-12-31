package com.mabahmani.imdb_scraping.util

import android.view.View
import androidx.databinding.BindingAdapter
import timber.log.Timber

@BindingAdapter(value = ["bind:rectangleBackgroundColor", "bind:rectangleBackgroundRadius"], requireAll = false)
fun setRectangleBackground(view: View, rectangleBackgroundColor: Int, rectangleBackgroundRadius:Float = 0f) {
    Timber.d("setRectangleBackground %s", "asdasd","asdasdsad")
    view.background = Shape.createRectangleWithCustomRadiusShape(
        rectangleBackgroundColor,
        rectangleBackgroundRadius.dp(),
        rectangleBackgroundRadius.dp(),
        rectangleBackgroundRadius.dp(),
        rectangleBackgroundRadius.dp()
    )
}
