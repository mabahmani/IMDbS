package com.mabahmani.imdb_scraping.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.util.Shape
import com.mabahmani.imdb_scraping.util.dp

class TitleWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    init {

        inflate(context, R.layout.custom_title_widget, this)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TitleWidget,
            0, 0).apply {
            try {
                val titleText = getString(R.styleable.TitleWidget_TW_Title)
                val subtitleText = getString(R.styleable.TitleWidget_TW_Subtitle)
                val titleColor = getColor(R.styleable.TitleWidget_TW_TitleColor,ContextCompat.getColor(context, R.color.blue_gray_900))
                val subtitleColor = getColor(R.styleable.TitleWidget_TW_SubtitleColor,ContextCompat.getColor(context, R.color.blue_gray_500))
                val dividerColor = getColor(R.styleable.TitleWidget_TW_DividerColor,ContextCompat.getColor(context, R.color.yellow_500))
                val showSeeMore = getBoolean(R.styleable.TitleWidget_TW_ShowSeeMore, false)

                val title = findViewById<AppCompatTextView>(R.id.title)
                val subtitle = findViewById<AppCompatTextView>(R.id.subtitle)
                val divider = findViewById<View>(R.id.divider)
                val seeMoreParent = findViewById<View>(R.id.seeMoreParent)

                title.text = titleText
                title.setTextColor(titleColor)

                if (subtitleText.isNullOrEmpty()){
                    subtitle.visibility = View.GONE
                }
                else{
                    subtitle.text = subtitleText
                    subtitle.setTextColor(subtitleColor)
                }


                divider.background = Shape.createRectangleWithCustomRadiusShape(
                    dividerColor,
                    2f.dp(),
                    2f.dp(),
                    2f.dp(),
                    2f.dp()
                )

                if (showSeeMore){
                    seeMoreParent.visibility = VISIBLE
                }
                else{
                    seeMoreParent.visibility = GONE
                }
            } finally {
                recycle()
            }
        }
    }

    fun setTitle(title: String){
        findViewById<AppCompatTextView>(R.id.title).text = title
    }

    fun setSubtitle(subtitle: String){
        val subtitleView = findViewById<AppCompatTextView>(R.id.subtitle)
        subtitleView.text = subtitle
        subtitleView.visibility = VISIBLE
    }
}