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
import com.mabahmani.domain.vo.TitleFullCasts
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.FragmentTitleFullcastBinding
import com.mabahmani.imdb_scraping.ui.main.title.adapter.TitleFullCastAdapter
import com.mabahmani.imdb_scraping.ui.main.title.state.TitleFullCastsUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.vm.TitleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TitleFullCastsFragment: Fragment() {

    lateinit var binding: FragmentTitleFullcastBinding
    private val viewModel: TitleViewModel by viewModels()
    lateinit var titleId: String
    lateinit var titleFullCasts: TitleFullCasts

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTitleFullcastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkArguments()
        observeTitleFullCastsUiState()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.titleCover.setOnClickListener{
            findNavController().navigate(
                R.id.imageViewerFragment,
                Bundle().apply {
                    putString("imageUrl", titleFullCasts.cover.getOriginalImageSizeUrl())
                    putString("title", titleFullCasts.name)
                }
            )
        }
    }

    private fun observeTitleFullCastsUiState() {

        viewModel.launchGetTitleFullCastsUseCase(TitleId(titleId))

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.titleFullCastsUiState.collect {
                    handleTitleFullCastsStates(it)
                }
            }
        }
    }

    private fun handleTitleFullCastsStates(state: TitleFullCastsUiState) {
        when (state) {
            is TitleFullCastsUiState.Loading -> {
                showLoading()
            }
            is TitleFullCastsUiState.ShowTitleFullCasts -> {
                titleFullCasts = state.titleFullCasts
                showFullCasts(state.titleFullCasts)
                hideLoading()
            }
            is TitleFullCastsUiState.Error -> {
                showError(state.message)
            }
            is TitleFullCastsUiState.NetworkError -> {
                showNetworkError()
            }
        }
    }

    private fun showFullCasts(titleAwards: TitleFullCasts) {

        binding.titleCoverUrl = titleAwards.cover.getCustomImageWidthUrl(512)
        binding.title = titleAwards.name  + " "  + titleAwards.year

        val adapter = TitleFullCastAdapter()

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter

        adapter.submitList(titleAwards.casts)
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