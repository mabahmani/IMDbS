package com.mabahmani.imdb_scraping.ui.main.search.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mabahmani.domain.vo.Suggestion
import com.mabahmani.domain.vo.common.Either
import com.mabahmani.domain.vo.common.Id
import com.mabahmani.domain.vo.enum.SuggestionType
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.FragmentSuggestionResultBinding
import com.mabahmani.imdb_scraping.ui.main.search.adapter.SuggestionAdapter
import com.mabahmani.imdb_scraping.ui.main.search.state.SuggestionsUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.util.toast
import com.mabahmani.imdb_scraping.vm.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SuggestionResultFragment : Fragment() {

    lateinit var binding: FragmentSuggestionResultBinding

    private val viewModel: SearchViewModel by viewModels()

    lateinit var suggestionType: SuggestionType

    lateinit var adapter: SuggestionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSuggestionResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkArguments()
        setupList()
        observerParentSearchView()
        initSuggestStateObserver()
    }

    private fun setupList() {

        adapter = SuggestionAdapter { it, video ->

            if (it != null){
                when (it.evalId()) {
                    is Id.TitleId -> {
                        findNavController().navigate(
                            R.id.titleDetailsFragment,
                            Bundle().apply {
                                putString("titleId", it.id)
                                putString("title", it.name)
                            }
                        )
                    }

                    is Id.NameId -> {
                        findNavController().navigate(
                            R.id.nameDetailsFragment,
                            Bundle().apply {
                                putString("nameId", it.id)
                                putString("name", it.name)
                            }
                        )
                    }
                }
            }


            else{
                when (video?.videoId?.validate()) {
                    is Either.Right -> {
                        findNavController().navigate(R.id.videoDetailsFragment,
                            Bundle().apply {
                                putString("videoId", video.videoId.value)
                                putString("title", video.title)
                            }
                        )
                    }

                    else -> {
                        requireContext().toast(getString(R.string.invalid_video_id))
                    }
                }
            }
        }


        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter
    }

    private fun initSuggestStateObserver() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.suggestionsUiState.collect {
                    handleSuggestStates(it)
                }
            }
        }
    }

    private fun handleSuggestStates(state: SuggestionsUiState) {
        Timber.d("handleSuggestStates %s", state)

        when (state) {
            is SuggestionsUiState.Idle -> {
                hideLoading()
            }
            is SuggestionsUiState.Loading -> {
                showLoading()
            }
            is SuggestionsUiState.ShowSearchData -> {
                showSuggestions(state.suggestions)
            }
            is SuggestionsUiState.Error -> {
                showError(state.message)
            }
            is SuggestionsUiState.NetworkError -> {
                showNetworkError()
            }
            SuggestionsUiState.TimeOutError -> retry()
        }
    }

    private fun retry() {
        checkLastInput()
    }

    private fun showNetworkError() {
        requireContext().showNetworkConnectionError {
            checkLastInput()
        }

    }

    private fun showError(message: String) {
        requireContext().showUnexpectedError(message)
    }

    private fun showSuggestions(suggestions: List<Suggestion>) {

        Timber.d("showSuggestions %s", suggestions)

        adapter.submitList(suggestions)
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
        suggestionType = when (requireArguments().getInt("suggestType")) {
            0 -> SuggestionType.ALL
            1 -> SuggestionType.TITLE
            2 -> SuggestionType.CELEB
            else -> SuggestionType.ALL
        }
    }

    private fun observerParentSearchView() {
        SuggestionFragment.searchView?.doAfterTextChanged {
            if (!it.isNullOrEmpty()) {
                when (suggestionType) {
                    SuggestionType.ALL -> viewModel.launchSuggestUseCase(it.toString())
                    SuggestionType.TITLE -> viewModel.launchSuggestTitleUseCase(it.toString())
                    SuggestionType.CELEB -> viewModel.launchSuggestCelebUseCase(it.toString())
                }
            }
        }
    }


    private fun checkLastInput() {
        val it = SuggestionFragment.searchView?.text

        if (!it.isNullOrEmpty()) {
            when (suggestionType) {
                SuggestionType.ALL -> viewModel.launchSuggestUseCase(it.toString())
                SuggestionType.TITLE -> viewModel.launchSuggestTitleUseCase(it.toString())
                SuggestionType.CELEB -> viewModel.launchSuggestCelebUseCase(it.toString())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkLastInput()
    }

}