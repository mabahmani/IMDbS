package com.mabahmani.imdb_scraping.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.mabahmani.domain.vo.common.Trailer
import com.mabahmani.imdb_scraping.databinding.FragmentHomeBinding
import com.mabahmani.imdb_scraping.ui.custom.RoundedPagerIndicatorDecoration
import com.mabahmani.imdb_scraping.ui.main.home.state.HomeUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.vm.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment: Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeScroll()
        initHomeStateObserver()


    }

    private fun observeScroll() {
        binding.appBar.setBackgroundAlpha(0)
        binding.nestedParent.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY <= 255){
                binding.appBar.setBackgroundAlpha(scrollY)
            }
            else{
                binding.appBar.setBackgroundAlpha(255)
            }
        })
    }

    private fun initHomeStateObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.homeUiState.collect{
                    handleStates(it)
                }
            }
        }
    }

    private fun handleStates(state: HomeUiState) {
        when(state){
            is HomeUiState.Loading ->{
                showLoading()
            }
            is HomeUiState.ShowNetworkError ->{
                showNetworkError()
            }
            is HomeUiState.ShowTrailers ->{
                showTrailers(state.trailers)
            }
            is HomeUiState.ShowError ->{
                showError(state.message)
            }
        }
    }

    private fun showNetworkError() {

        requireContext().showNetworkConnectionError{
            viewModel.launchHomeUseCase()
        }
    }

    private fun showError(message: String) {
        Timber.d("showError %s", message)
    }

    private fun showTrailers(trailers: List<Trailer>) {
        val adapter = HomeTrailerAdapter{

        }
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.trailersList)
        binding.trailersList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.trailersList.addItemDecoration(RoundedPagerIndicatorDecoration())
        binding.trailersList.adapter = adapter

        adapter.submitList(trailers)

        binding.shimmer.stopShimmer()
        binding.shimmer.visibility = View.GONE
    }

    private fun showLoading() {
        binding.shimmer.visibility = View.VISIBLE
        binding.shimmer.startShimmer()
    }
}