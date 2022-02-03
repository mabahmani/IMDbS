package com.mabahmani.imdb_scraping.ui.main.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AdvancedSearchFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                return AdvancedTitleSearchFragment()
            }
            1 -> {
                return AdvancedNameSearchFragment()
            }
            else -> Fragment()
        }
    }
}