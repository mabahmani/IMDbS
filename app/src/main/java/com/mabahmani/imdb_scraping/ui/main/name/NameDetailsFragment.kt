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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mabahmani.domain.vo.NameDetails
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.imdb_scraping.databinding.FragmentNameDetailsBinding
import com.mabahmani.imdb_scraping.ui.main.name.state.NameDetailUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.vm.NameViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NameDetailsFragment: Fragment() {

    private val viewModel: NameViewModel by viewModels()

    lateinit var binding: FragmentNameDetailsBinding
    lateinit var nameId: String
    lateinit var name: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNameDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkArguments()
        setupAppBar()
        observeNameDetailsUiState()
    }

    private fun observeNameDetailsUiState() {

        viewModel.launchGetNameDetailsUseCase(NameId(nameId))

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.nameDetailsUiState.collect {
                    handleNameDetailsStates(it)
                }
            }
        }
    }

    private fun handleNameDetailsStates(state: NameDetailUiState) {
        when (state) {
            is NameDetailUiState.Loading -> {
                showLoading()
            }
            is NameDetailUiState.ShowNameDetails -> {
                showNameOverview(state.nameDetails)
                showNamePhotos(state.nameDetails)
                showNameKnowFor(state.nameDetails)
                showNameFilmographies(state.nameDetails)
                showNameRelatedVideos(state.nameDetails)
                showNamePersonalDetails(state.nameDetails)
                hideLoading()
            }
            is NameDetailUiState.Error -> {
                showError(state.message)
            }
            is NameDetailUiState.NetworkError -> {
                showNetworkError()
            }
        }
    }

    private fun showNamePersonalDetails(nameDetails: NameDetails) {
        val adapter = NameDetailsPersonalDetailsAdapter{

        }

        binding.personalDetailsList.layoutManager = LinearLayoutManager(requireContext())
        binding.personalDetailsList.adapter = adapter

        adapter.submitList(nameDetails.personalDetails)
    }

    private fun showNameRelatedVideos(nameDetails: NameDetails) {
        val adapter = NameDetailsRelatedVideoAdapter{

        }

        binding.relatedVideoList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.relatedVideoList.adapter = adapter

        adapter.submitList(nameDetails.relatedVideos)
    }

    private fun showNameFilmographies(nameDetails: NameDetails) {
        val adapter = NameDetailsFilmographyAdapter{

        }

        binding.filmographyList.layoutManager = LinearLayoutManager(requireContext())
        binding.filmographyList.adapter = adapter

        adapter.submitList(nameDetails.filmographies)

    }

    private fun showNameKnowFor(nameDetails: NameDetails) {
        val adapter = NameDetailsKnownForAdapter{

        }

        binding.knownForList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.knownForList.adapter = adapter

        adapter.submitList(nameDetails.knownForTitles)
    }

    private fun showNamePhotos(nameDetails: NameDetails) {
        val adapter = NameDetailsPhotoAdapter{

        }

        binding.photoList.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.photoList.adapter = adapter

        adapter.submitList(nameDetails.photos)
    }

    private fun showNameOverview(nameDetails: NameDetails) {
        binding.nameSkills = nameDetails.jobTitles.joinToString(" | ")
        binding.avatarUrl = nameDetails.avatar.getCustomImageWidthUrl(256)
        binding.trailerCoverUrl = nameDetails.trailer.preview.getCustomImageWidthUrl(512)
        binding.trailerInfo = nameDetails.trailer.title
        binding.bio = nameDetails.bioSummary
        binding.bornInfo = nameDetails.birthDateMonthDay + ", " + nameDetails.birthDate + " in " + nameDetails.birthPlace
    }

    private fun showError(message: String) {
        requireContext().showUnexpectedError()
        viewModel.launchGetNameDetailsUseCase(NameId(nameId))
    }

    private fun showNetworkError() {
        requireContext().showNetworkConnectionError {
            viewModel.launchGetNameDetailsUseCase(NameId(nameId))
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

    private fun setupAppBar() {
        binding.appBar.getTitleView()?.text = name
    }

    private fun checkArguments() {
        nameId = requireArguments().getString("nameId", "")
        name = requireArguments().getString("name", "")
    }
}