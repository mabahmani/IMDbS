package com.mabahmani.imdb_scraping.ui.main.search.fragment

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
import com.mabahmani.domain.vo.common.Genre
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.FragmentGenresBinding
import com.mabahmani.imdb_scraping.ui.main.search.adapter.GenresAdapter
import com.mabahmani.imdb_scraping.ui.main.search.state.GenresUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.vm.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GenresFragment : Fragment() {

    lateinit var binding: FragmentGenresBinding

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGenresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initGenresStateObserver()
    }

    private fun initGenresStateObserver() {

        viewModel.launchGetGenresUseCase()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.genresUiState.collect {
                    handleGenresStates(it)
                }
            }
        }
    }

    private fun handleGenresStates(state: GenresUiState) {

        when (state) {
            is GenresUiState.Loading -> {
                showLoading()
            }
            is GenresUiState.ShowSearchData -> {
                showGenres(state.genres)
            }
            is GenresUiState.Error -> {
                showError(state.message)
            }
            is GenresUiState.NetworkError -> {
                showNetworkError()
            }
            GenresUiState.TimeOutError -> retry()
        }
    }

    private fun retry() {
        viewModel.launchGetGenresUseCase()
    }

    private fun showNetworkError() {
        requireContext().showNetworkConnectionError {
            viewModel.launchGetGenresUseCase()
        }
    }

    private fun showError(message: String) {
        requireContext().showUnexpectedError(message)
    }

    private fun showGenres(genres: List<Genre>) {

        val adapter = GenresAdapter {

            if (it.name.contains("superhero", true)) {
                findNavController().navigate(R.id.searchTitlesByKeywordFragment,
                    Bundle().apply {
                        putString("keyword", it.name)
                    })
            } else {
                findNavController().navigate(R.id.searchTitlesByGenreFragment,
                    Bundle().apply {
                        when {
                            it.name.contains("-romance", true) -> {
                                putString("genre", "Comedy,Romance")
                            }
                            it.name.contains("-comedy", true) -> {
                                putString("genre", "Action,Comedy")
                            }
                            else -> {
                                putString("genre", it.name)
                            }
                        }
                    }
                )
            }

        }

        binding.list.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.list.adapter = adapter

        adapter.submitList(genres)

        hideLoading()
    }

    private fun hideLoading() {
        binding.shimmer.visibility = View.GONE
        binding.shimmer.stopShimmer()
    }

    private fun showLoading() {
        binding.shimmer.visibility = View.VISIBLE
        binding.shimmer.startShimmer()
    }
}