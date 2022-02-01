package com.mabahmani.imdb_scraping.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.util.Shape
import com.mabahmani.imdb_scraping.util.dp

class ActionBarWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    init {

        inflate(context, R.layout.custom_action_bar_widget, this)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ActionBarWidget,
            0, 0).apply {
            try {

                val titleText = getString(R.styleable.ActionBarWidget_ABW_Title)
                val actionIconDrawable = getDrawable(R.styleable.ActionBarWidget_ABW_ActionIcon)
                val showBackAction = getBoolean(R.styleable.ActionBarWidget_ABW_ShowBackAction, true)
                val showSearchIcon = getBoolean(R.styleable.ActionBarWidget_ABW_ShowSearchIcon, true)
                val type = getInt(R.styleable.ActionBarWidget_ABW_Type, 0)


                val searchBar = findViewById<ConstraintLayout>(R.id.searchBar)
                val contentParent = findViewById<ConstraintLayout>(R.id.contentParent)
                val back = findViewById<AppCompatImageView>(R.id.back)
                val title = findViewById<AppCompatTextView>(R.id.title)
                val searchIcon = findViewById<AppCompatImageView>(R.id.searchIcon)
                val actionIcon = findViewById<AppCompatImageView>(R.id.actionIcon)


                if (actionIconDrawable == null && !showBackAction){
                    back.visibility = GONE
                    actionIcon.visibility = GONE
                    val param =  contentParent.layoutParams as MarginLayoutParams
                    param.setMargins(16.dp(),0,16.dp(),0)
                    contentParent.layoutParams = param
                }

                else if (actionIconDrawable == null){
                    actionIcon.visibility = GONE
                    val param =  contentParent.layoutParams as MarginLayoutParams
                    param.setMargins(0,0,16.dp(),0)
                    contentParent.layoutParams = param
                }

                else if (!showBackAction){
                    back.visibility = GONE
                    val param =  contentParent.layoutParams as MarginLayoutParams
                    param.setMargins(16.dp(),0,0,0)
                    contentParent.layoutParams = param
                }

                if (type == 0){
                    searchBar.visibility = GONE
                    title.visibility = VISIBLE
                }

                else{
                    searchBar.visibility = VISIBLE
                    title.visibility = GONE
                }

                if (showSearchIcon)
                    searchIcon.visibility = VISIBLE
                else
                    searchIcon.visibility = GONE

                searchBar.background = Shape.createRectangleWithCustomRadiusShape(
                    ContextCompat.getColor(context, R.color.blue_gray_100),
                    12f.dp(),
                    12f.dp(),
                    12f.dp(),
                    12f.dp()
                )

                title.text = titleText
                actionIcon.setImageDrawable(actionIconDrawable)


                back.setOnClickListener {
                    findNavController().popBackStack()
                }

            } finally {
                recycle()
            }
        }
    }

    fun setBackgroundAlpha(alpha: Int){
       findViewById<AppBarLayout>(R.id.actionBar).background.alpha = alpha
    }

    fun getActionView(): AppCompatImageView? {
        return findViewById(R.id.actionIcon)
    }

    fun getSearchView(): AppCompatEditText? {
        return findViewById(R.id.searchInput)
    }

    fun getParentView(): ConstraintLayout? {
        return findViewById(R.id.contentParent)
    }
}