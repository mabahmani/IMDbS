package com.mabahmani.imdb_scraping.ui.main.name.fragment

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
import com.mabahmani.domain.vo.NameAwards
import com.mabahmani.domain.vo.common.EventId
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.FragmentNameAwardsBinding
import com.mabahmani.imdb_scraping.ui.main.name.adapter.NameEventsAdapter
import com.mabahmani.imdb_scraping.ui.main.name.state.NameAwardUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.vm.NameViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NameAwardFragment: Fragment() {

    lateinit var binding: FragmentNameAwardsBinding
    private val viewModel: NameViewModel by viewModels()
    lateinit var nameId: String
    lateinit var nameAwards: NameAwards

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNameAwardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkArguments()
        observeNameAwardUiState()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.avatarParent.setOnClickListener {
            findNavController().navigate(
                R.id.imageViewerFragment,
                Bundle().apply {
                    putString("imageUrl", nameAwards.avatar.getOriginalImageSizeUrl())
                    putString("title", nameAwards.name)
                }
            )
        }
    }

    private fun observeNameAwardUiState() {

        viewModel.launchGetNameAwardsUseCase(NameId(nameId))

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.nameAwardUiState.collect {
                    handleNameAwardStates(it)
                }
            }
        }
    }

    private fun handleNameAwardStates(state: NameAwardUiState) {
        when (state) {
            is NameAwardUiState.Loading -> {
                showLoading()
            }
            is NameAwardUiState.ShowNameAwards -> {
                nameAwards = state.nameAwards
                showAwards(state.nameAwards)
                hideLoading()
            }
            is NameAwardUiState.Error -> {
                showError(state.message)
            }
            is NameAwardUiState.NetworkError -> {
                showNetworkError()
            }
            NameAwardUiState.TimeOutError -> retry()
        }
    }

    private fun retry() {
        viewModel.launchGetNameAwardsUseCase(NameId(nameId))
    }

    private fun showAwards(nameAwards: NameAwards) {

        binding.avatarUrl = nameAwards.avatar.getCustomImageWidthUrl(320)
        binding.name = nameAwards.name

        val adapter = NameEventsAdapter{eventId: EventId, eventYear: String, eventName: String ->
            findNavController().navigate(R.id.eventDetailsFragment,
                Bundle().apply {
                    putString("eventId", eventId.value)
                    putString("eventYear", eventYear)
                    putString("eventName", eventName)
                }
            )
        }

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter

        adapter.submitList(nameAwards.events)
    }

    private fun showError(message: String) {
        requireContext().showUnexpectedError(message)
    }

    private fun showNetworkError() {
        requireContext().showNetworkConnectionError {
            viewModel.launchGetNameAwardsUseCase(NameId(nameId))
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
        nameId = requireArguments().getString("nameId", "")
    }
}