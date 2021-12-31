package com.mabahmani.imdb_scraping.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView

class RecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    init {
        overScrollMode = OVER_SCROLL_NEVER
    }
}