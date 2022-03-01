package com.mabahmani.imdb_scraping.util

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.mabahmani.imdb_scraping.R


@BindingAdapter("bind:imageUrl")
fun setRemoteImage(view: AppCompatImageView, imageUrl: String?) {

    val shimmer = Shimmer.AlphaHighlightBuilder()
        .setDuration(1500)
        .setBaseAlpha(0.9f)
        .setHighlightAlpha(0.8f)
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(true)
        .build()

    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmer)
    }

    view.setBackgroundColor(view.context.getColor(R.color.gray_800))

    Glide.with(view)
        .setDefaultRequestOptions(RequestOptions().placeholder(shimmerDrawable).error(R.drawable.ic_bxs_bug).fitCenter())
        .load(imageUrl)
        .transition(DrawableTransitionOptions.withCrossFade()).into(view)
}

@BindingAdapter(
    value = ["bind:rectangleBackgroundColor", "bind:rectangleBackgroundRadius"],
    requireAll = false
)
fun setRectangleBackground(
    view: View,
    rectangleBackgroundColor: Int,
    rectangleBackgroundRadius: Float = 0f
) {
    view.background = Shape.createRectangleWithCustomRadiusShape(
        rectangleBackgroundColor,
        rectangleBackgroundRadius.dp(),
        rectangleBackgroundRadius.dp(),
        rectangleBackgroundRadius.dp(),
        rectangleBackgroundRadius.dp()
    )
}
