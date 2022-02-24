package com.mabahmani.imdb_scraping.ui.main.trailers

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
import androidx.recyclerview.widget.GridLayoutManager
import com.mabahmani.domain.vo.common.Either
import com.mabahmani.domain.vo.common.Trailer
import com.mabahmani.domain.vo.enum.TrailerType
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.FragmentTrailersResultBinding
import com.mabahmani.imdb_scraping.ui.main.trailers.state.TrailersUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.util.toast
import com.mabahmani.imdb_scraping.vm.TrailerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrailersResultFragment: Fragment() {

    lateinit var binding: FragmentTrailersResultBinding
    lateinit var type: TrailerType
    private val viewModel: TrailerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrailersResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkArguments()
        launchUseCase()
        observeUiState()
    }


    private fun checkArguments() {

        when (requireArguments().getInt("trailerType")) {
            0 -> type = TrailerType.ANTICIPATED
            1 -> type = TrailerType.TRENDING
            2 -> type = TrailerType.POPULAR
            3 -> type = TrailerType.RECENT
        }

    }

    private fun launchUseCase() {
        when (type) {
            TrailerType.ANTICIPATED -> {
                viewModel.launchGetAnticipatedTrailersUseCase()
            }
            TrailerType.TRENDING -> {
                viewModel.launchGetTrendingTrailersUseCase()
            }
            TrailerType.POPULAR -> {
                viewModel.launchGetPopularTrailersUseCase()
            }
            TrailerType.RECENT -> {
                viewModel.launchGetRecentTrailersUseCase()
            }
            else -> {
                viewModel.launchGetTrendingTrailersUseCase()
            }
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.trailersUiState.collect {
                    handleTrailersStates(it)
                }
            }
        }
    }

    private fun handleTrailersStates(state: TrailersUiState) {
        when (state) {
            is TrailersUiState.Loading -> {
                showLoading()
            }
            is TrailersUiState.ShowTrailersData -> {
                showTrailers(state.trailers)
            }
            is TrailersUiState.Error -> {
                showError(state.message)
            }
            is TrailersUiState.NetworkError -> {
                showNetworkError()
            }
        }
    }

    private fun showTrailers(trailers: List<Trailer>) {
        val adapter = TrailersAdapter{it, playVideo ->

            if (playVideo){
                when (it.videoId.validate()) {
                    is Either.Right -> {
                        findNavController().navigate(
                            R.id.videoDetailsFragment,
                            Bundle().apply {
                                putString("videoId", it.videoId.value)
                                putString("title", it.title)
                            }
                        )
                    }

                    else -> {
                        requireContext().toast(getString(R.string.invalid_video_id))
                    }
                }
            }

            else{
                when (it.titleId.validate()) {
                    is Either.Right -> {
                        findNavController().navigate(
                            R.id.titleDetailsFragment,
                            Bundle().apply {
                                putString("titleId", it.titleId.value)
                                putString("title", it.title)
                            }
                        )
                    }

                    else -> {
                        requireContext().toast(getString(R.string.invalid_title_id))
                    }
                }
            }

        }

        binding.list.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.list.adapter = adapter

        adapter.submitList(trailers)

        hideLoading()
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

    private fun showLoading() {
        binding.shimmer.visibility = View.VISIBLE
        binding.shimmer.startShimmer()
    }

    private fun hideLoading() {
        binding.shimmer.visibility = View.GONE
        binding.shimmer.stopShimmer()
    }


}