package com.mabahmani.imdb_scraping.ui.main.image.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mabahmani.imdb_scraping.databinding.FragmentImageViewerBinding

class ImageViewerFragment: Fragment() {

    lateinit var binding: FragmentImageViewerBinding
    lateinit var imageUrl: String
    lateinit var title: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkArguments()
        setupAppBar()
        setupImageView()

    }

    private fun setupImageView() {
        binding.imageUrl = imageUrl
    }

    private fun setupAppBar() {
        binding.appBar.getTitleView()?.text = title
    }

    private fun checkArguments() {
        imageUrl = requireArguments().getString("imageUrl", "")
        title = requireArguments().getString("title", "")
    }
}