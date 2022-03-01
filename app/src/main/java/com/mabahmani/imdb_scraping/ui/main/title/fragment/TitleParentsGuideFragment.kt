package com.mabahmani.imdb_scraping.ui.main.title.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mabahmani.domain.vo.TitleParentsGuide
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.FragmentTitleParentsGuideBinding
import com.mabahmani.imdb_scraping.ui.main.title.adapter.TitleParentsGuideAdapter
import com.mabahmani.imdb_scraping.ui.main.title.adapter.TitleParentsGuideCertificateAdapter
import com.mabahmani.imdb_scraping.ui.main.title.state.TitleParentsGuideUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.vm.TitleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TitleParentsGuideFragment: Fragment() {

    lateinit var binding: FragmentTitleParentsGuideBinding
    private val viewModel: TitleViewModel by viewModels()
    lateinit var titleId: String
    lateinit var titleParentsGuide: TitleParentsGuide

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTitleParentsGuideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkArguments()
        observeTitleParentsGuideUiState()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.titleCover.setOnClickListener{
            findNavController().navigate(
                R.id.imageViewerFragment,
                Bundle().apply {
                    putString("imageUrl", titleParentsGuide.cover.getOriginalImageSizeUrl())
                    putString("title", titleParentsGuide.name)
                }
            )
        }
    }

    private fun observeTitleParentsGuideUiState() {

        viewModel.launchGetTitleParentsGuideUseCase(TitleId(titleId))

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.titleParentsGuideUiState.collect {
                    handleTitleParentsGuideStates(it)
                }
            }
        }
    }

    private fun handleTitleParentsGuideStates(state: TitleParentsGuideUiState) {
        when (state) {
            is TitleParentsGuideUiState.Loading -> {
                showLoading()
            }
            is TitleParentsGuideUiState.ShowTitleParentsGuide -> {
                titleParentsGuide = state.titleParentsGuide
                showTitleParentsGuide(state.titleParentsGuide)
                hideLoading()
            }
            is TitleParentsGuideUiState.Error -> {
                showError(state.message)
            }
            is TitleParentsGuideUiState.NetworkError -> {
                showNetworkError()
            }
        }
    }

    private fun showTitleParentsGuide(titleParentsGuide: TitleParentsGuide) {

        binding.titleCoverUrl = titleParentsGuide.cover.getCustomImageWidthUrl(512)
        binding.title = titleParentsGuide.name + " " + titleParentsGuide.year

        setupCertificates(titleParentsGuide)
        setupGuides(titleParentsGuide)
        setupSpoilerGuides(titleParentsGuide)
    }

    private fun setupSpoilerGuides(titleParentsGuide: TitleParentsGuide) {

        if (titleParentsGuide.spoilGuides.isEmpty()){
            binding.spoilerBanner.visibility = View.GONE
        }

        else{
            binding.spoilerBanner.visibility = View.VISIBLE

            val adapter = TitleParentsGuideAdapter()

            binding.spoilerGuideList.layoutManager = LinearLayoutManager(requireContext())
            binding.spoilerGuideList.adapter = adapter

            adapter.submitList(titleParentsGuide.spoilGuides)
        }

    }

    private fun setupGuides(titleParentsGuide: TitleParentsGuide) {
        val adapter = TitleParentsGuideAdapter()

        binding.guideList.layoutManager = LinearLayoutManager(requireContext())
        binding.guideList.adapter = adapter

        adapter.submitList(titleParentsGuide.noSpoilGuides)
    }

    private fun setupCertificates(titleParentsGuide: TitleParentsGuide) {
        val adapter = TitleParentsGuideCertificateAdapter()

        binding.certificationList.layoutManager = LinearLayoutManager(requireContext())
        binding.certificationList.adapter = adapter

        adapter.submitList(titleParentsGuide.certifications)
    }

    private fun showError(message: String) {
        requireContext().showUnexpectedError()
        viewModel.launchGetTitleFullCastsUseCase(TitleId(titleId))
    }

    private fun showNetworkError() {
        requireContext().showNetworkConnectionError {
            viewModel.launchGetTitleFullCastsUseCase(TitleId(titleId))
        }
    }

    private fun showLoading() {
        binding.mainView.visibility = View.GONE
        binding.shimmer.visibility = View.VISIBLE
        binding.shimmer.startShimmer()
    }

    private fun hideLoading() {
        binding.shimmer.visibility = View.GONE
        binding.shimmer.stopShimmer()
        binding.mainView.visibility = View.VISIBLE
    }

    private fun checkArguments() {
        titleId = requireArguments().getString("titleId", "")
    }
}