package com.mabahmani.imdb_scraping.ui.main.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mabahmani.domain.vo.enum.SuggestionType

class SuggestionFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val fragment = SuggestionResultFragment()
                fragment.arguments = Bundle().apply { putInt("suggestType", SuggestionType.ALL.ordinal) }
                return fragment
            }
            1 -> {
                val fragment = SuggestionResultFragment()
                fragment.arguments = Bundle().apply { putInt("suggestType", SuggestionType.TITLE.ordinal) }
                return fragment
            }
            2 ->{
                val fragment = SuggestionResultFragment()
                fragment.arguments = Bundle().apply { putInt("suggestType", SuggestionType.CELEB.ordinal) }
                return fragment
            }
            else -> Fragment()
        }
    }
}