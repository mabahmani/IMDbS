package com.mabahmani.imdb_scraping.ui.main.charts

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
import com.mabahmani.domain.vo.common.BoxOffice
import com.mabahmani.domain.vo.enum.ChartType
import com.mabahmani.imdb_scraping.databinding.FragmentChartsBoxofficeBinding
import com.mabahmani.imdb_scraping.ui.main.charts.state.ChartBoxOfficeUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.vm.ChartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChartBoxOfficeFragment : Fragment() {
    lateinit var binding: FragmentChartsBoxofficeBinding
    private val viewModel: ChartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChartsBoxofficeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchUseCase()
        observeUiState()
    }

    private fun launchUseCase() {
        viewModel.launchGetBoxOfficeUseCase()
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.boxOfficeUiState.collect {
                    handleBoxOfficeStates(it)
                }
            }
        }
    }

    private fun handleBoxOfficeStates(state: ChartBoxOfficeUiState) {
        when (state) {
            is ChartBoxOfficeUiState.Loading -> {
                showLoading()
            }
            is ChartBoxOfficeUiState.ShowBoxOfficeData -> {
                showBoxOffice(state.boxOffice)
            }
            is ChartBoxOfficeUiState.Error -> {
                showError(state.message)
            }
            is ChartBoxOfficeUiState.NetworkError -> {
                showNetworkError()
            }
        }
    }

    private fun showNetworkError() {
        requireContext().showNetworkConnectionError {
            launchUseCase()
        }
    }

    private fun showError(message: String) {
        requireContext().showUnexpectedError()
        launchUseCase()
    }

    private fun showBoxOffice(boxOffice: BoxOffice) {

        binding.captionText = boxOffice.startDate

        val adapter = ChartBoxOfficeAdapter {

        }

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter

        adapter.submitList(boxOffice.boxOfficeItems)

        hideLoading()
    }

    private fun showLoading() {
        binding.shimmer.visibility = View.VISIBLE
        binding.shimmer.startShimmer()
    }

    private fun hideLoading() {
        binding.shimmer.visibility = View.GONE
        binding.shimmer.stopShimmer()
    }
}