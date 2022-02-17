package com.mabahmani.imdb_scraping.ui.main.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mabahmani.domain.vo.TitleTechnicalSpecs
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.imdb_scraping.databinding.FragmentTitleTechnicalSpecsBinding
import com.mabahmani.imdb_scraping.ui.main.title.state.TitleTechnicalSpecsUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.vm.TitleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TitleTechnicalSpecsFragment: Fragment() {

    lateinit var binding: FragmentTitleTechnicalSpecsBinding
    private val viewModel: TitleViewModel by viewModels()
    lateinit var titleId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTitleTechnicalSpecsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkArguments()
        observeTitleTechnicalSpecsUiState()
    }

    private fun observeTitleTechnicalSpecsUiState() {

        viewModel.launchGetTitleTechnicalSpecsUseCase(TitleId(titleId))

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.titleTechnicalSpecsUiState.collect {
                    handleTitleTechnicalSpecsStates(it)
                }
            }
        }
    }

    private fun handleTitleTechnicalSpecsStates(state: TitleTechnicalSpecsUiState) {
        when (state) {
            is TitleTechnicalSpecsUiState.Loading -> {
                showLoading()
            }
            is TitleTechnicalSpecsUiState.ShowTitleTechnicalSpecs -> {
                showTechnicalSpecs(state.titleTechnicalSpecs)
                hideLoading()
            }
            is TitleTechnicalSpecsUiState.Error -> {
                showError(state.message)
            }
            is TitleTechnicalSpecsUiState.NetworkError -> {
                showNetworkError()
            }
        }
    }

    private fun showTechnicalSpecs(titleTechnicalSpecs: TitleTechnicalSpecs) {

        binding.titleCoverUrl = titleTechnicalSpecs.cover.getCustomImageWidthUrl(512)
        binding.title = titleTechnicalSpecs.name  + " "  + titleTechnicalSpecs.year

        val adapter = TitleDetailsTechnicalSpecsAdapter()

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter

        adapter.submitList(titleTechnicalSpecs.items)
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