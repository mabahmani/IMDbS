package com.mabahmani.imdb_scraping.ui.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.google.android.material.checkbox.MaterialCheckBox
import com.mabahmani.domain.vo.enum.TitleType
import com.mabahmani.imdb_scraping.databinding.FragmentAdvancedNameSearchBinding
import com.mabahmani.imdb_scraping.databinding.FragmentAdvancedTitleSearchBinding

class AdvancedTitleSearchFragment: Fragment() {

    lateinit var binding: FragmentAdvancedTitleSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdvancedTitleSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}