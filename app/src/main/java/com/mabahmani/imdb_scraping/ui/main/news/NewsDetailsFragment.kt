package com.mabahmani.imdb_scraping.ui.main.news

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
import com.mabahmani.domain.vo.NewsDetails
import com.mabahmani.domain.vo.common.NewsId
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.FragmentNewsDetailsBinding
import com.mabahmani.imdb_scraping.ui.main.news.state.NewsDetailsUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.vm.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsDetailsFragment : Fragment() {

    lateinit var binding: FragmentNewsDetailsBinding

    private val viewModel: NewsViewModel by viewModels()

    lateinit var newsId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkArguments()
        initNewsDetailsStateObserver()
    }

    private fun checkArguments() {
        newsId = requireArguments().getString("newsId", "")
    }

    private fun initNewsDetailsStateObserver() {

        viewModel.launchGetNewsDetailsUseCase(NewsId(newsId))

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.newsDetailsUiState.collect {
                    handleNewsDetailsStates(it)
                }
            }
        }
    }

    private fun handleNewsDetailsStates(state: NewsDetailsUiState) {

        when (state) {
            is NewsDetailsUiState.Loading -> {
                showLoading()
            }
            is NewsDetailsUiState.ShowNewsDetails -> {
                showNewsDetails(state.newsDetails)
            }
            is NewsDetailsUiState.Error -> {
                showError(state.message)
            }
            is NewsDetailsUiState.NetworkError -> {
                showNetworkError()
            }
        }
    }

    private fun showNetworkError() {
        requireContext().showNetworkConnectionError {
            viewModel.launchGetNewsDetailsUseCase(NewsId(newsId))
        }
    }

    private fun showError(message: String) {
        requireContext().showUnexpectedError()
        viewModel.launchGetNewsDetailsUseCase(NewsId(newsId))
    }

    private fun showNewsDetails(newsDetails: NewsDetails) {

        binding.title = newsDetails.title
        binding.subtitle = newsDetails.date + " | " + newsDetails.author + " | " + newsDetails.newsAgency
        binding.imageUrl = newsDetails.image.getCustomImageWidthUrl(512)
        binding.content = newsDetails.content

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