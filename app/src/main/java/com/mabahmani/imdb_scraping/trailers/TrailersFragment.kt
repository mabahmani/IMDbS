package com.mabahmani.imdb_scraping.trailers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mabahmani.imdb_scraping.databinding.FragmentChartsBinding
import com.mabahmani.imdb_scraping.databinding.FragmentHomeBinding
import com.mabahmani.imdb_scraping.databinding.FragmentSearchBinding
import com.mabahmani.imdb_scraping.databinding.FragmentTrailersBinding

class TrailersFragment: Fragment() {

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
    }
}