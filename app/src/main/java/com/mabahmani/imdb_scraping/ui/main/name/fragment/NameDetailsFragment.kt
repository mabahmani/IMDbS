package com.mabahmani.imdb_scraping.ui.main.name.fragment

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.mabahmani.domain.vo.NameDetails
import com.mabahmani.domain.vo.common.Either
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.FragmentNameDetailsBinding
import com.mabahmani.imdb_scraping.ui.main.name.adapter.*
import com.mabahmani.imdb_scraping.ui.main.name.state.NameDetailUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.util.toast
import com.mabahmani.imdb_scraping.vm.NameViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NameDetailsFragment: Fragment() {

    private val viewModel: NameViewModel by viewModels()

    lateinit var binding: FragmentNameDetailsBinding
    lateinit var nameId: String
    lateinit var name: String
    lateinit var nameDetails: NameDetails

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
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.bioTitle.setOnClickListener {
            findNavController().navigate(R.id.nameBiographyFragment,
                Bundle().apply {
                    putString("nameId", nameId)
                }
            )
        }

        binding.photos.setOnClickListener {
            findNavController().navigate(
                R.id.imagesFragment,
                Bundle().apply { putString("id", nameId); putString("title", name) })
        }

        binding.seeAwards.setOnClickListener {
            findNavController().navigate(R.id.nameAwardFragment,
                Bundle().apply {
                    putString("nameId", nameId)
                }
            )
        }

        binding.trailerParent.setOnClickListener {
            when (nameDetails.trailer.videoId.validate()) {
                is Either.Right -> {
                    findNavController().navigate(R.id.videoDetailsFragment,
                        Bundle().apply {
                            putString("videoId", nameDetails.trailer.videoId.value)
                            putString("title", name)
                        }
                    )
                }

                else -> {
                    requireContext().toast(getString(R.string.invalid_video_id))
                }
            }
        }

        binding.relatedVideosTitle.setOnClickListener {
            findNavController().navigate(R.id.videosFragment, Bundle().apply { putString("id", nameId); putString("title", name) })
        }

        binding.avatarParent.setOnClickListener {
            findNavController().navigate(
                R.id.imageViewerFragment,
                Bundle().apply {
                    putString("imageUrl", nameDetails.avatar.getOriginalImageSizeUrl())
                    putString("title", name)
                }
            )
        }
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
                nameDetails = state.nameDetails
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
            when (it.videoId.validate()) {
                is Either.Right -> {
                    findNavController().navigate(R.id.videoDetailsFragment,
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

        binding.relatedVideoList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.relatedVideoList.adapter = adapter

        adapter.submitList(nameDetails.relatedVideos)
    }

    private fun showNameFilmographies(nameDetails: NameDetails) {
        val adapter = NameDetailsFilmographyAdapter()

        binding.filmographyList.layoutManager = LinearLayoutManager(requireContext())
        binding.filmographyList.adapter = adapter

        adapter.submitList(nameDetails.filmographies)

    }

    private fun showNameKnowFor(nameDetails: NameDetails) {
        val adapter = NameDetailsKnownForAdapter{
            when (it.titleId?.validate()) {
                is Either.Right -> {
                    findNavController().navigate(R.id.titleDetailsFragment,
                        Bundle().apply {
                            putString("titleId", it.titleId?.value)
                            putString("title", it.title)
                        }
                    )
                }

                else -> {
                    requireContext().toast(getString(R.string.invalid_title_id))
                }
            }
        }

        binding.knownForList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.knownForList.adapter = adapter

        adapter.submitList(nameDetails.knownForTitles)
    }

    private fun showNamePhotos(nameDetails: NameDetails) {
        val adapter = NameDetailsPhotoAdapter{
            findNavController().navigate(R.id.imageDetailsFragment,
                Bundle().apply {
                    putString("id", nameId)
                    putString("imageId", it.imageId?.value)
                    putString("title", name)
                }
            )
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