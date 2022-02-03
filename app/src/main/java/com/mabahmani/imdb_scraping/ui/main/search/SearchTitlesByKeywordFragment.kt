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
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.mabahmani.domain.vo.common.Title
import com.mabahmani.imdb_scraping.databinding.FragmentSearchTitlesByGenreBinding
import com.mabahmani.imdb_scraping.databinding.FragmentSearchTitlesByKeywordBinding
import com.mabahmani.imdb_scraping.databinding.FragmentTitlesBinding
import com.mabahmani.imdb_scraping.ui.main.search.state.TitlesUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.vm.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException

@AndroidEntryPoint
class SearchTitlesByKeywordFragment : Fragment (){

    lateinit var binding: FragmentSearchTitlesByKeywordBinding

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: TitlesAdapter
    private lateinit var keyword: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchTitlesByKeywordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkArguments()
        setupAppBar()
        setupList()
        initTitlesStateObserver()

    }

    private fun checkArguments() {
        keyword = requireArguments().getString("keyword", "")
    }

    private fun setupAppBar() {
        binding.appBar.getTitleView()?.text  = keyword
    }

    private fun setupList() {

        adapter = TitlesAdapter{

        }

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter
    }

    private fun initTitlesStateObserver() {
        viewModel.launchSearchTitlesByKeywordsUseCase(keyword)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.titlesUiState.collect {
                    handleTitlesStates(it)
                }
            }
        }
    }

    private fun handleTitlesStates(state: TitlesUiState) {
        when (state) {
            is TitlesUiState.Loading -> {
                showLoading()
            }
            is TitlesUiState.ShowSearchData -> {
                showTitles(state.pagingData)
            }
        }
    }


    private fun showTitles(titles: Flow<PagingData<Title>>) {

        viewLifecycleOwner.lifecycleScope.launch {
            titles.collectLatest {
                adapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch{
            adapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    is LoadState.Loading -> {
                        showLoading()
                    }
                    !is LoadState.Loading -> {
                        hideLoading()
                    }
                    is LoadState.Error -> {
                        val error = (it.refresh as LoadState.Error).error
                        when((it.refresh as LoadState.Error).error){
                            is UnknownHostException -> showNetworkError()
                            else -> showError(error.message.orEmpty())
                        }
                    }
                }

                if (it.append is LoadState.Error){
                    val error = (it.append as LoadState.Error).error
                    when((it.append as LoadState.Error).error){
                        is UnknownHostException -> showNetworkError()
                        else -> showError(error.message.orEmpty())
                    }
                }

            }
        }





    }

    private fun showNetworkError() {
        requireContext().showNetworkConnectionError {
            adapter.retry()
        }
    }

    private fun showError(message: String) {
        requireContext().showUnexpectedError()
        adapter.retry()
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