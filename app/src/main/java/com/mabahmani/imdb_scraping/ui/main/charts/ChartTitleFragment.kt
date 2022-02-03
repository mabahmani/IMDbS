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
import com.mabahmani.domain.vo.common.Title
import com.mabahmani.domain.vo.enum.ChartType
import com.mabahmani.imdb_scraping.databinding.FragmentChartsTitlesBinding
import com.mabahmani.imdb_scraping.ui.main.charts.state.ChartUiState
import com.mabahmani.imdb_scraping.ui.main.search.state.GenresUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.vm.ChartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChartTitleFragment : Fragment() {
    lateinit var binding: FragmentChartsTitlesBinding
    lateinit var type: ChartType
    private val viewModel: ChartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChartsTitlesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkArguments()
        launchUseCase()
        observeUiState()
    }

    private fun launchUseCase() {
        when (type) {
            ChartType.TOP_MOVIE -> {
                binding.captionText = "Top 250 Movies as rated by IMDb voters"
                viewModel.launchGetTop250MoviesUseCase()
            }
            ChartType.TOP_TV -> {
                binding.captionText = "Top 250 TV Shows as rated by IMDb voters"
                viewModel.launchGetTop250TvShowsUseCase()
            }
            ChartType.POPULAR_MOVIE -> {
                binding.captionText = "Most Popular Movies"
                viewModel.launchGetPopularMoviesUseCase()
            }
            ChartType.POPULAR_TV -> {
                binding.captionText = "Most Popular TV Shows"
                viewModel.launchGetPopularTvShowsUseCase()
            }
            ChartType.BOTTOM_MOVIE -> {
                binding.captionText = "Bottom 100 as voted by IMDb voters"
                viewModel.launchGetBottom100MoviesUseCase()
            }
            else -> {
                viewModel.launchGetTop250MoviesUseCase()
            }
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.chartsUiState.collect {
                    handleChartsStates(it)
                }
            }
        }
    }

    private fun handleChartsStates(state: ChartUiState) {
        when (state) {
            is ChartUiState.Loading -> {
                showLoading()
            }
            is ChartUiState.ShowChartsData -> {
                showTitles(state.titles)
            }
            is ChartUiState.Error -> {
                showError(state.message)
            }
            is ChartUiState.NetworkError -> {
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

    private fun showTitles(titles: List<Title>) {
        val adapter = ChartTitleAdapter {

        }

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter

        adapter.submitList(titles)

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

    private fun checkArguments() {

        when (requireArguments().getInt("chartType")) {
            0 -> type = ChartType.TOP_MOVIE
            1 -> type = ChartType.TOP_TV
            2 -> type = ChartType.POPULAR_MOVIE
            3 -> type = ChartType.POPULAR_TV
            4 -> type = ChartType.BOTTOM_MOVIE
            5 -> type = ChartType.BOX_OFFICE
        }

    }
}