package com.mabahmani.imdb_scraping.ui.main.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mabahmani.domain.vo.TitleDetails
import com.mabahmani.domain.vo.common.Either
import com.mabahmani.domain.vo.common.Text
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.FragmentTitleDetailsBinding
import com.mabahmani.imdb_scraping.ui.main.title.state.TitleDetailUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.util.toast
import com.mabahmani.imdb_scraping.vm.TitleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class TitleDetailsFragment : Fragment() {

    lateinit var binding: FragmentTitleDetailsBinding
    lateinit var titleId: String
    lateinit var title: String
    lateinit var titleDetails: TitleDetails

    private val viewModel: TitleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTitleDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkArguments()
        setupAppBar()
        observeTitleDetailsUiState()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.seeAwards.setOnClickListener {
            findNavController().navigate(
                R.id.titleAwardFragment,
                Bundle().apply { putString("titleId", titleId) })
        }

        binding.fullCasts.setOnClickListener {
            findNavController().navigate(
                R.id.titleFullCastsFragment,
                Bundle().apply { putString("titleId", titleId) })
        }

        binding.parentsGuideView.setOnClickListener {
            findNavController().navigate(
                R.id.titleParentsGuideFragment,
                Bundle().apply { putString("titleId", titleId) })
        }

        binding.technicalSpecs.setOnClickListener {
            findNavController().navigate(
                R.id.titleTechnicalSpecsFragment,
                Bundle().apply { putString("titleId", titleId) })
        }

        binding.photos.setOnClickListener {
            findNavController().navigate(
                R.id.imagesFragment,
                Bundle().apply { putString("id", titleId); putString("title", title) })
        }

        binding.videos.setOnClickListener {
            findNavController().navigate(
                R.id.videosFragment,
                Bundle().apply { putString("id", titleId); putString("title", title) })
        }

        binding.trailerParent.setOnClickListener {
            when (titleDetails.trailer.videoId.validate()) {
                is Either.Right -> {
                    findNavController().navigate(R.id.videoDetailsFragment,
                        Bundle().apply {
                            putString("videoId", titleDetails.trailer.videoId.value)
                            putString("title", title)
                        }
                    )
                }

                else -> {
                    requireContext().toast(getString(R.string.invalid_video_id))
                }
            }
        }
    }

    private fun observeTitleDetailsUiState() {

        viewModel.launchGetTitleDetailsUseCase(TitleId(titleId))

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.titleDetailsUiState.collect {
                    handleTitleDetailsStates(it)
                }
            }
        }
    }

    private fun handleTitleDetailsStates(state: TitleDetailUiState) {
        when (state) {
            is TitleDetailUiState.Loading -> {
                showLoading()
            }
            is TitleDetailUiState.ShowTitleDetails -> {
                titleDetails = state.titleDetails
                showTitleOverview(state.titleDetails)
                showVideos(state.titleDetails)
                showPhotos(state.titleDetails)
                showCasts(state.titleDetails)
                showMoreLikeThis(state.titleDetails)
                showStoryLine(state.titleDetails)
                showTopReview(state.titleDetails)
                showDetails(state.titleDetails)
                showBoxOffice(state.titleDetails)
                showTechnicalSpecs(state.titleDetails)
                hideLoading()
            }
            is TitleDetailUiState.Error -> {
                showError(state.message)
            }
            is TitleDetailUiState.NetworkError -> {
                showNetworkError()
            }
        }
    }

    private fun showTechnicalSpecs(titleDetails: TitleDetails) {

        val result: MutableList<Text> = mutableListOf()

        titleDetails.technicalSpecs.forEach() {
            if (it.subtitle.isNotEmpty())
                result.add(it)
        }

        val adapter = TitleDetailsTechnicalSpecsAdapter()

        binding.technicalSpecsList.layoutManager = LinearLayoutManager(requireContext())
        binding.technicalSpecsList.adapter = adapter

        adapter.submitList(result)
    }

    private fun showBoxOffice(titleDetails: TitleDetails) {
        binding.grossUsAndCanada = titleDetails.boxOffice.grossUsAndCanada
        binding.openingWeekendUsAndCanada = titleDetails.boxOffice.openingWeekendUsAndCanada
        binding.grossWorldwide = titleDetails.boxOffice.grossWorldwide
        binding.budget = titleDetails.boxOffice.budget
    }

    private fun showDetails(titleDetails: TitleDetails) {
        binding.releaseDateDetails = titleDetails.details.releaseDate.joinToString()
        binding.countryOfOrigins = titleDetails.details.countryOfOrigin.joinToString()
        binding.officialSites = titleDetails.details.officialSites.joinToString()
        binding.languages = titleDetails.details.languages.joinToString()
        binding.filmingLocations = titleDetails.details.filmingLocations.joinToString()
        binding.productionCompanies = titleDetails.details.productionCompanies.joinToString()
    }

    private fun showTopReview(titleDetails: TitleDetails) {
        binding.review = titleDetails.topReview.review
        binding.reviewTitle = titleDetails.topReview.title
        binding.reviewRate = titleDetails.topReview.rate
        binding.reviewUsername = titleDetails.topReview.username
        binding.reviewDate = titleDetails.topReview.date
    }

    private fun showStoryLine(titleDetails: TitleDetails) {
        binding.story = titleDetails.storyLine.story
        binding.tagline = titleDetails.storyLine.taglines
        binding.genres = titleDetails.storyLine.genres.joinToString()
        binding.motionPictureRating = titleDetails.storyLine.motionPictureRating
    }

    private fun showMoreLikeThis(titleDetails: TitleDetails) {
        val adapter = TitleDetailsRelatedMoviesAdapter {
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

        binding.moreLikeThisList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.moreLikeThisList.adapter = adapter

        adapter.submitList(titleDetails.relatedTitles)
    }

    private fun showCasts(titleDetails: TitleDetails) {
        val adapter = TitleDetailsCastsAdapter {
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

        binding.topCastList.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.topCastList.adapter = adapter

        adapter.submitList(titleDetails.topCasts)
    }

    private fun showPhotos(titleDetails: TitleDetails) {
        val adapter = TitleDetailsPhotosAdapter {
            Timber.d("showPhotos %s", it)
            findNavController().navigate(R.id.imageDetailsFragment,
                Bundle().apply {
                    putString("id", titleId)
                    putString("imageId", it.imageId?.value)
                    putString("title", title)
                }
            )
        }

        binding.photoList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.photoList.adapter = adapter

        adapter.submitList(titleDetails.photos)
    }

    private fun showVideos(titleDetails: TitleDetails) {
        val adapter = TitleDetailsVideosAdapter {
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

        binding.videoList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.videoList.adapter = adapter

        adapter.submitList(titleDetails.videos)
    }

    private fun showTitleOverview(titleDetails: TitleDetails) {
        binding.baseInfo =
            titleDetails.releaseYear + " • " + titleDetails.certificate + " • " + titleDetails.runtime
        binding.rate = titleDetails.imdbRating
        binding.voteCount = "| " + titleDetails.numberOfVotes
        binding.coverUrl = titleDetails.cover.getCustomImageWidthUrl(512)
        binding.trailerCoverUrl = titleDetails.trailer.preview.getCustomImageWidthUrl(512)
        binding.trailerInfo = titleDetails.trailer.runtime
        binding.plot = titleDetails.plot
        binding.directors = titleDetails.directors.joinToString { it.name }
        binding.writers = titleDetails.directors.joinToString { it.name }
        binding.stars = titleDetails.stars.joinToString { it.name }

        val adapter = TitleDetailsGenresAdapter {
            findNavController().navigate(R.id.searchTitlesByGenreFragment,
                Bundle().apply {
                    putString("genre", it)
                }
            )
        }

        binding.genresList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.genresList.adapter = adapter

        adapter.submitList(titleDetails.genres)
    }

    private fun hideLoading() {
        binding.mainView.visibility = View.VISIBLE
        binding.shimmer.visibility = View.GONE
        binding.shimmer.stopShimmer()
    }

    private fun showLoading() {
        binding.mainView.visibility = View.GONE
        binding.shimmer.visibility = View.VISIBLE
        binding.shimmer.startShimmer()
    }

    private fun showNetworkError() {
        requireContext().showNetworkConnectionError {
            viewModel.launchGetTitleDetailsUseCase(TitleId(titleId))
        }
    }

    private fun showError(message: String) {
        requireContext().showUnexpectedError()
        viewModel.launchGetTitleDetailsUseCase(TitleId(titleId))
    }

    private fun setupAppBar() {
        binding.appBar.getTitleView()?.text = title
    }

    private fun checkArguments() {
        titleId = requireArguments().getString("titleId", "")
        title = requireArguments().getString("title", "")
    }
}