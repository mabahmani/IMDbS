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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mabahmani.domain.vo.Calender
import com.mabahmani.domain.vo.common.Genre
import com.mabahmani.imdb_scraping.databinding.FragmentCalenderBinding
import com.mabahmani.imdb_scraping.ui.main.search.state.CalenderUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.vm.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CalenderFragment : Fragment() {

    lateinit var binding: FragmentCalenderBinding

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalenderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCalenderStateObserver()
    }

    private fun initCalenderStateObserver() {

        viewModel.launchGetCalenderUseCase()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.calenderUiState.collect {
                    handleCalenderStates(it)
                }
            }
        }
    }

    private fun handleCalenderStates(state: CalenderUiState) {

        when (state) {
            is CalenderUiState.Loading -> {
                showLoading()
            }
            is CalenderUiState.ShowSearchData -> {
                showCalender(state.calenders)
            }
            is CalenderUiState.Error -> {
                showError(state.message)
            }
            is CalenderUiState.NetworkError -> {
                showNetworkError()
            }
        }
    }

    private fun showNetworkError() {
        requireContext().showNetworkConnectionError {
            viewModel.launchGetCalenderUseCase()
        }
    }

    private fun showError(message: String) {
        requireContext().showUnexpectedError()
        viewModel.launchGetCalenderUseCase()
    }

    private fun showCalender(calenders: List<Calender>) {

        val adapter = CalenderAdapter {

        }

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter

        adapter.submitList(calenders)

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