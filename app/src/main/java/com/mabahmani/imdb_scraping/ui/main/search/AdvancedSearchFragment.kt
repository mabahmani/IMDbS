package com.mabahmani.imdb_scraping.ui.main.search

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.FragmentSuggestionBinding
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.doAfterTextChanged
import com.mabahmani.imdb_scraping.databinding.FragmentAdvancedSearchBinding
import com.mabahmani.imdb_scraping.util.showKeyboard
import timber.log.Timber


class AdvancedSearchFragment: Fragment() {

    lateinit var binding: FragmentAdvancedSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdvancedSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAppBarr()
        setupViewPager()
        setupTabLayout()
    }

    private fun setupAppBarr() {

    }

    private fun setupViewPager() {

        binding.viewPager.apply {
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            adapter = AdvancedSearchFragmentStateAdapter(this@AdvancedSearchFragment)
        }

    }

    private fun setupTabLayout() {

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position){
                0 -> tab.text = getString(R.string.advanced_title_search)
                1 -> tab.text = getString(R.string.advanced_name_search)
            }
        }.attach()

        setTabLayoutFont()
    }

    private fun setTabLayoutFont() {

        val vg = binding.tabLayout.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount
        for (j in 0 until tabsCount) {
            val vgTab = vg.getChildAt(j) as ViewGroup
            val tabChildrenCount = vgTab.childCount
            for (i in 0 until tabChildrenCount) {
                val tabViewChild = vgTab.getChildAt(i)
                if (tabViewChild is TextView) {
                    tabViewChild.textSize = 10f

                    tabViewChild.setTypeface(
                        Typeface.createFromAsset(resources.assets,resources.getString(
                            R.string.font_medium)), Typeface.NORMAL)


                }
            }
        }

    }

}