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
import com.mabahmani.domain.vo.common.Genre
import com.mabahmani.imdb_scraping.databinding.FragmentKeywordsBinding
import com.mabahmani.imdb_scraping.ui.main.search.state.KeywordsUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.vm.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class KeywordsFragment : Fragment() {

    lateinit var binding: FragmentKeywordsBinding

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKeywordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initKeywordsStateObserver()
    }

    private fun initKeywordsStateObserver() {

        viewModel.launchGetKeywordsUseCase()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.keywordsUiState.collect {
                    handleKeywordsStates(it)
                }
            }
        }
    }

    private fun handleKeywordsStates(state: KeywordsUiState) {

        when (state) {
            is KeywordsUiState.Loading -> {
                showLoading()
            }
            is KeywordsUiState.ShowSearchData -> {
                showKeywords(state.keywords)
            }
            is KeywordsUiState.Error -> {
                showError(state.message)
            }
            is KeywordsUiState.NetworkError -> {
                showNetworkError()
            }
        }
    }

    private fun showNetworkError() {
        requireContext().showNetworkConnectionError {
            viewModel.launchGetGenresUseCase()
        }
    }

    private fun showError(message: String) {
        requireContext().showUnexpectedError()
        viewModel.launchGetGenresUseCase()
    }

    private fun showKeywords(keywords: List<String>) {

        val adapter = KeywordsAdapter {

        }

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter

        adapter.submitList(keywords)

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