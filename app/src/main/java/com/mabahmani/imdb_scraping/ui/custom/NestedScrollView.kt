package com.mabahmani.imdb_scraping.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView

class NestedScrollView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {
    init {
        overScrollMode = OVER_SCROLL_NEVER
    }
}