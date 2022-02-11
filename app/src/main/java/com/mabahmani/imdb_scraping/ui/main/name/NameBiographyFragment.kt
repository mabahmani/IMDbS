package com.mabahmani.imdb_scraping.ui.main.name

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
import com.mabahmani.domain.vo.NameBio
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.imdb_scraping.databinding.FragmentNameBiographyBinding
import com.mabahmani.imdb_scraping.ui.main.name.state.NameBioUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.vm.NameViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NameBiographyFragment: Fragment() {

    lateinit var binding: FragmentNameBiographyBinding
    private val viewModel: NameViewModel by viewModels()
    lateinit var nameId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNameBiographyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkArguments()
        observeNameBioUiState()
    }

    private fun observeNameBioUiState() {

        viewModel.launchGetNameBioUseCase(NameId(nameId))

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.nameBioUiState.collect {
                    handleNameBioStates(it)
                }
            }
        }
    }

    private fun handleNameBioStates(state: NameBioUiState) {
        when (state) {
            is NameBioUiState.Loading -> {
                showLoading()
            }
            is NameBioUiState.ShowNameBio -> {
                showOverview(state.nameBio)
                showBio(state.nameBio)
                showFamily(state.nameBio)
                showTradeMark(state.nameBio)
                showTrivia(state.nameBio)
                showSalary(state.nameBio)
                hideLoading()
            }
            is NameBioUiState.Error -> {
                showError(state.message)
            }
            is NameBioUiState.NetworkError -> {
                showNetworkError()
            }
        }
    }

    private fun showSalary(nameBio: NameBio) {
        val adapter = NameBiographySalaryAdapter()

        binding.salaryList.layoutManager = LinearLayoutManager(requireContext())
        binding.salaryList.adapter = adapter

        adapter.submitList(nameBio.salary)
    }

    private fun showTrivia(nameBio: NameBio) {
        val adapter = NameBiographyTriviaAdapter()

        binding.triviaList.layoutManager = LinearLayoutManager(requireContext())
        binding.triviaList.adapter = adapter

        adapter.submitList(nameBio.trivia)
    }

    private fun showTradeMark(nameBio: NameBio) {
        val adapter = NameBiographyTradeMarkAdapter()

        binding.tradeMarkList.layoutManager = LinearLayoutManager(requireContext())
        binding.tradeMarkList.adapter = adapter

        adapter.submitList(nameBio.trademark)
    }

    private fun showFamily(nameBio: NameBio) {
        val adapter = NameBiographyFamilyAdapter()

        binding.familyList.layoutManager = LinearLayoutManager(requireContext())
        binding.familyList.adapter = adapter

        adapter.submitList(nameBio.family)
    }

    private fun showBio(nameBio: NameBio) {
        binding.miniBio = nameBio.miniBio
    }

    private fun showOverview(nameBio: NameBio) {

        binding.avatarUrl = nameBio.avatar.getCustomImageWidthUrl(320)
        binding.name = nameBio.name

        val adapter = NameBiographyOverviewAdapter()

        binding.overviewList.layoutManager = LinearLayoutManager(requireContext())
        binding.overviewList.adapter = adapter

        adapter.submitList(nameBio.overview)
    }

    private fun showError(message: String) {
        requireContext().showUnexpectedError()
        viewModel.launchGetNameBioUseCase(NameId(nameId))
    }

    private fun showNetworkError() {
        requireContext().showNetworkConnectionError {
            viewModel.launchGetNameBioUseCase(NameId(nameId))
        }
    }

    private fun showLoading() {
        binding.mainView.visibility = View.GONE
        binding.shimmer.visibility = View.VISIBLE
        binding.shimmer.startShimmer()
    }

    private fun hideLoading() {
        binding.shimmer.visibility = View.GONE
        binding.shimmer.stopShimmer()
        binding.mainView.visibility = View.VISIBLE
    }

    private fun checkArguments() {
        nameId = requireArguments().getString("nameId", "")
    }
}