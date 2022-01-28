package com.mabahmani.imdb_scraping.ui.main.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SearchFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> GenresFragment()
            1 -> NamesFragment()
            2 -> TitlesFragment()
            3 -> KeywordsFragment()
            else -> Fragment()
        }
    }
}