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
import com.mabahmani.domain.vo.TitleAwards
import com.mabahmani.domain.vo.common.EventId
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.FragmentTitleAwardsBinding
import com.mabahmani.imdb_scraping.ui.main.title.adapter.TitleEventsAdapter
import com.mabahmani.imdb_scraping.ui.main.title.state.TitleAwardsUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.vm.TitleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TitleAwardFragment: Fragment() {

    lateinit var binding: FragmentTitleAwardsBinding
    private val viewModel: TitleViewModel by viewModels()
    lateinit var titleId: String
    lateinit var titleAwards: TitleAwards

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTitleAwardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkArguments()
        observeTitleAwardUiState()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.titleCover.setOnClickListener{
            findNavController().navigate(R.id.imageViewerFragment,
                Bundle().apply {
                    putString("imageUrl", titleAwards.cover.getOriginalImageSizeUrl())
                    putString("title", titleAwards.name)
                }
            )
        }
    }

    private fun observeTitleAwardUiState() {

        viewModel.launchGetTitleAwardsUseCase(TitleId(titleId))

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.titleAwardsUiState.collect {
                    handleTitleAwardStates(it)
                }
            }
        }
    }

    private fun handleTitleAwardStates(state: TitleAwardsUiState) {
        when (state) {
            is TitleAwardsUiState.Loading -> {
                showLoading()
            }
            is TitleAwardsUiState.ShowTitleAwards -> {
                titleAwards = state.titleAwards
                showAwards(state.titleAwards)
                hideLoading()
            }
            is TitleAwardsUiState.Error -> {
                showError(state.message)
            }
            is TitleAwardsUiState.NetworkError -> {
                showNetworkError()
            }
            TitleAwardsUiState.TimeOutError -> retry()
        }
    }

    private fun retry() {
        viewModel.launchGetTitleAwardsUseCase(TitleId(titleId))
    }

    private fun showAwards(titleAwards: TitleAwards) {

        binding.titleCoverUrl = titleAwards.cover.getCustomImageWidthUrl(512)
        binding.title = titleAwards.name

        val adapter = TitleEventsAdapter{ eventId: EventId, eventYear: String, eventName: String ->
            findNavController().navigate(
                R.id.eventDetailsFragment,
                Bundle().apply {
                    putString("eventId", eventId.value)
                    putString("eventYear", eventYear)
                    putString("eventName", eventName)
                }
            )
        }

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter

        adapter.submitList(titleAwards.events)
    }

    private fun showError(message: String) {
        requireContext().showUnexpectedError(message)
    }

    private fun showNetworkError() {
        requireContext().showNetworkConnectionError {
            viewModel.launchGetTitleAwardsUseCase(TitleId(titleId))
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