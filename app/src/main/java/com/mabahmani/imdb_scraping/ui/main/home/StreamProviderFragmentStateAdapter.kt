package com.mabahmani.imdb_scraping.ui.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mabahmani.data.util.Constants
import com.mabahmani.domain.vo.HomeExtra

class StreamProviderFragmentStateAdapter(fragment: Fragment, private val list:List<HomeExtra.StreamProvider>): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = StreamProviderFragment()
        fragment.arguments = Bundle().apply {
            putSerializable("streamProviderTitles", list[position])
        }
        return  fragment
    }
}