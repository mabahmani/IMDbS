package com.mabahmani.imdb_scraping.ui.main.search.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.FragmentSearchBinding
import com.mabahmani.imdb_scraping.ui.main.search.adapter.SearchFragmentStateAdapter

class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAppBarr()
        setupViewPager()
        setupTabLayout()
    }

    private fun setupAppBarr() {

        binding.appBar.getActionView()?.setOnClickListener {
            findNavController().navigate(R.id.calenderFragment)
        }

        binding.appBar.getSearchView()?.isFocusable = false
        binding.appBar.getSearchView()?.setOnClickListener {
            findNavController().navigate(R.id.suggestionFragment)
        }
        binding.appBar.getParentView()?.setOnClickListener {
            findNavController().navigate(R.id.suggestionFragment)
        }
    }

    private fun setupViewPager() {

        binding.viewPager.apply {
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            adapter = SearchFragmentStateAdapter(this@SearchFragment)
        }

    }

    private fun setupTabLayout() {

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.genres)
                1 -> tab.text = getString(R.string.celebs)
                2 -> tab.text = getString(R.string.titles)
                3 -> tab.text = getString(R.string.keywords)
                4 -> tab.text = getString(R.string.events)
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
                        Typeface.createFromAsset(
                            resources.assets, resources.getString(
                                R.string.font_medium
                            )
                        ), Typeface.NORMAL
                    )


                }
            }
        }

    }
}