package com.mabahmani.imdb_scraping.ui.main.search.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mabahmani.imdb_scraping.ui.main.search.fragment.*

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
            4 -> EventsFragment()
            else -> Fragment()
        }
    }
}