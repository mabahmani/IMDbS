package com.mabahmani.imdb_scraping.ui.main.search.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.FragmentSuggestionBinding
import com.mabahmani.imdb_scraping.ui.main.search.adapter.SuggestionFragmentStateAdapter
import com.mabahmani.imdb_scraping.util.showKeyboard


class SuggestionFragment: Fragment() {

    lateinit var binding: FragmentSuggestionBinding

    companion object{
        var searchView : AppCompatEditText? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSuggestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAppBarr()
        setupViewPager()
        setupTabLayout()
    }

    private fun setupAppBarr() {
        searchView = binding.appBar.getSearchView()
    }

    private fun setupViewPager() {

        binding.viewPager.apply {
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            adapter = SuggestionFragmentStateAdapter(this@SuggestionFragment)
        }

    }

    private fun setupTabLayout() {

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position){
                0 -> tab.text = getString(R.string.all)
                1 -> tab.text = getString(R.string.titles)
                2 -> tab.text = getString(R.string.celebs)
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

    override fun onResume() {
        super.onResume()
        focusOnSearchViewAndOpenKeyboard()
    }

    private fun focusOnSearchViewAndOpenKeyboard() {
       binding.appBar.getSearchView()?.showKeyboard()
    }
}