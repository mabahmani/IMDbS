package com.mabahmani.imdb_scraping.ui.main.trailers

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mabahmani.domain.vo.enum.ChartType
import com.mabahmani.domain.vo.enum.TrailerType

class TrailersFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 ->{
                val fragment = TrailersResultFragment()
                fragment.arguments = Bundle().apply {
                    putInt("trailerType", TrailerType.TRENDING.ordinal)
                }
                return fragment
            }
            1 -> {
                val fragment = TrailersResultFragment()
                fragment.arguments = Bundle().apply {
                    putInt("trailerType", TrailerType.ANTICIPATED.ordinal)
                }
                return fragment
            }
            2 -> {
                val fragment = TrailersResultFragment()
                fragment.arguments = Bundle().apply {
                    putInt("trailerType", TrailerType.POPULAR.ordinal)
                }
                return fragment
            }
            3 ->{
                val fragment = TrailersResultFragment()
                fragment.arguments = Bundle().apply {
                    putInt("trailerType", TrailerType.RECENT.ordinal)
                }
                return fragment
            }
            else ->{
                val fragment = TrailersResultFragment()
                fragment.arguments = Bundle().apply {
                    putInt("trailerType", TrailerType.TRENDING.ordinal)
                }
                return fragment
            }
        }
    }
}