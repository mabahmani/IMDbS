package com.mabahmani.imdb_scraping.ui.main.search

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
import com.mabahmani.domain.vo.common.Event
import com.mabahmani.imdb_scraping.databinding.FragmentEventsBinding
import com.mabahmani.imdb_scraping.ui.main.search.state.EventsUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.vm.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EventsFragment : Fragment() {

    lateinit var binding: FragmentEventsBinding

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEventsStateObserver()
    }

    private fun initEventsStateObserver() {

        viewModel.launchGetEventsUseCase()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.eventsUiState.collect {
                    handleEventsStates(it)
                }
            }
        }
    }

    private fun handleEventsStates(state: EventsUiState) {

        when (state) {
            is EventsUiState.Loading -> {
                showLoading()
            }
            is EventsUiState.ShowSearchData -> {
                showEvents(state.events)
            }
            is EventsUiState.Error -> {
                showError(state.message)
            }
            is EventsUiState.NetworkError -> {
                showNetworkError()
            }
        }
    }

    private fun showNetworkError() {
        requireContext().showNetworkConnectionError {
            viewModel.launchGetEventsUseCase()
        }
    }

    private fun showError(message: String) {
        requireContext().showUnexpectedError()
        viewModel.launchGetEventsUseCase()
    }

    private fun showEvents(events: List<Event>) {

        val adapter = EventsAdapter {

        }

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter

        adapter.submitList(events)

        hideLoading()
    }

    private fun hideLoading(){
        binding.shimmer.visibility = View.GONE
        binding.shimmer.stopShimmer()
    }

    private fun showLoading() {
        binding.shimmer.visibility = View.VISIBLE
        binding.shimmer.startShimmer()
    }
}