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
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.mabahmani.domain.vo.common.Either
import com.mabahmani.domain.vo.common.Name
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.FragmentCelebsBinding
import com.mabahmani.imdb_scraping.ui.main.search.state.CelebsUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.util.toast
import com.mabahmani.imdb_scraping.vm.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.net.UnknownHostException

@AndroidEntryPoint
class NamesFragment : Fragment (){

    lateinit var binding: FragmentCelebsBinding

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: NamesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCelebsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupList()
        initCelebsStateObserver()

    }

    private fun setupList() {

        adapter = NamesAdapter{
            when (it.nameId.validate()) {
                is Either.Right -> {
                    findNavController().navigate(
                        R.id.nameDetailsFragment,
                        Bundle().apply {
                            putString("nameId", it.nameId.value)
                            putString("name", it.name)
                        }
                    )
                }

                else -> {
                    requireContext().toast(getString(R.string.invalid_name_id))
                }
            }
        }

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter
    }

    private fun initCelebsStateObserver() {
        viewModel.launchGetCelebsUseCase()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.celebsUiState.collect {
                    handleCelebsStates(it)
                }
            }
        }
    }

    private fun handleCelebsStates(state: CelebsUiState) {
        when (state) {
            is CelebsUiState.Loading -> {
                showLoading()
            }
            is CelebsUiState.ShowSearchData -> {
                showCelebs(state.pagingData)
            }
        }
    }

    private fun showCelebs(celebs: Flow<PagingData<Name>>) {

        viewLifecycleOwner.lifecycleScope.launch {
            celebs.collectLatest {
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