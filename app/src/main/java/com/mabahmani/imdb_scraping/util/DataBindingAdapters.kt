package com.mabahmani.imdb_scraping.util

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


@BindingAdapter("bind:imageUrl")
fun setRemoteImage(view: AppCompatImageView, imageUrl: String?) {
    if (imageUrl != null){
        Glide.with(view).load(imageUrl).transition(DrawableTransitionOptions.withCrossFade()).into(view)
    }
}

@BindingAdapter(value = ["bind:rectangleBackgroundColor", "bind:rectangleBackgroundRadius"], requireAll = false)
fun setRectangleBackground(view: View, rectangleBackgroundColor: Int, rectangleBackgroundRadius:Float = 0f) {
    view.background = Shape.createRectangleWithCustomRadiusShape(
        rectangleBackgroundColor,
        rectangleBackgroundRadius.dp(),
        rectangleBackgroundRadius.dp(),
        rectangleBackgroundRadius.dp(),
        rectangleBackgroundRadius.dp()
    )
}
