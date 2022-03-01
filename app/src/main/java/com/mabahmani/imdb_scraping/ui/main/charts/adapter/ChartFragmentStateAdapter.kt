package com.mabahmani.imdb_scraping.ui.main.charts.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mabahmani.domain.vo.enum.ChartType
import com.mabahmani.imdb_scraping.ui.main.charts.fragment.ChartBoxOfficeFragment
import com.mabahmani.imdb_scraping.ui.main.charts.fragment.ChartTitleFragment

class ChartFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val fragment = ChartTitleFragment()
                fragment.arguments = Bundle().apply {
                    putInt("chartType", ChartType.TOP_MOVIE.ordinal)
                }
                return fragment
            }
            1 -> {
                val fragment = ChartTitleFragment()
                fragment.arguments = Bundle().apply {
                    putInt("chartType", ChartType.TOP_TV.ordinal)
                }
                return fragment
            }
            2 -> {
                val fragment = ChartTitleFragment()
                fragment.arguments = Bundle().apply {
                    putInt("chartType", ChartType.POPULAR_MOVIE.ordinal)
                }
                return fragment
            }
            3 -> {
                val fragment = ChartTitleFragment()
                fragment.arguments = Bundle().apply {
                    putInt("chartType", ChartType.POPULAR_TV.ordinal)
                }
                return fragment
            }
            4 -> ChartBoxOfficeFragment()
            5 -> {
                val fragment = ChartTitleFragment()
                fragment.arguments = Bundle().apply {
                    putInt("chartType", ChartType.BOTTOM_MOVIE.ordinal)
                }
                return fragment
            }
            else -> {
                val fragment = ChartTitleFragment()
                fragment.arguments = Bundle().apply {
                    putInt("chartType", ChartType.TOP_MOVIE.ordinal)
                }
                return fragment
            }
        }
    }
}