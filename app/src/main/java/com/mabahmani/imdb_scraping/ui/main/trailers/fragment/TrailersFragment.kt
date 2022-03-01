package com.mabahmani.imdb_scraping.ui.main.trailers.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.FragmentTrailersBinding
import com.mabahmani.imdb_scraping.ui.main.trailers.adapter.TrailersFragmentStateAdapter

class TrailersFragment : Fragment() {

    lateinit var binding: FragmentTrailersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrailersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupTabLayout()
    }


    private fun setupViewPager() {

        binding.viewPager.apply {
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            adapter = TrailersFragmentStateAdapter(this@TrailersFragment)
        }

    }

    private fun setupTabLayout() {

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.trending)
                1 -> tab.text = getString(R.string.anticipated)
                2 -> tab.text = getString(R.string.popular)
                3 -> tab.text = getString(R.string.recent)
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