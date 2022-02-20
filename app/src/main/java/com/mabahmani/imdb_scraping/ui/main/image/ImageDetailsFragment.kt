package com.mabahmani.imdb_scraping.ui.main.image

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mabahmani.domain.vo.ImageDetails
import com.mabahmani.domain.vo.NameBio
import com.mabahmani.domain.vo.common.*
import com.mabahmani.imdb_scraping.databinding.FragmentImageDetailsBinding
import com.mabahmani.imdb_scraping.ui.main.image.state.ImageDetailsUiState
import com.mabahmani.imdb_scraping.ui.main.name.state.NameBioUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.vm.ImageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ImageDetailsFragment: Fragment() {

    lateinit var binding: FragmentImageDetailsBinding
    private val viewModel: ImageViewModel by viewModels()
    lateinit var id: String
    lateinit var imageId: String
    lateinit var title: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkArguments()
        setupAppBar()
        observeImageDetailsUiState()
    }

    private fun setupAppBar() {
        binding.appBar.getTitleView()?.text = title
    }

    private fun observeImageDetailsUiState() {

        launchUseCase()


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.imagesDetailsUiState.collect {
                    handleImageDetailsStates(it)
                }
            }
        }
    }

    private fun launchUseCase() {
        when {
            id.startsWith("tt") -> {
                viewModel.launchGetTitleImageDetailsUseCase(TitleId(id), ImageId(imageId))
            }
            id.startsWith("nm") -> {
                viewModel.launchGetNameImageDetailsUseCase(NameId(id), ImageId(imageId))
            }
            id.startsWith("rg") -> {
                viewModel.launchGetGalleryImageDetailsUseCase(GalleryId(id), ImageId(imageId))
            }
            else -> {
                viewModel.launchGetListImageDetailsUseCase(ListId(id), ImageId(imageId))
            }
        }
    }

    private fun handleImageDetailsStates(state: ImageDetailsUiState) {
        when (state) {
            is ImageDetailsUiState.Loading -> {
                showLoading()
            }
            is ImageDetailsUiState.ShowImageDetails -> {
                showImageDetails(state.imageDetails)
                hideLoading()
            }
            is ImageDetailsUiState.Error -> {
                showError(state.message)
            }
            is ImageDetailsUiState.NetworkError -> {
                showNetworkError()
            }
        }
    }

    private fun showImageDetails(imageDetails: ImageDetails) {

        if (imageDetails.images.isNotEmpty()){
            val details = imageDetails.images[0]

            binding.imageUrl = details.image.getOriginalImageSizeUrl()
            binding.title = details.title
            binding.description.text = Html.fromHtml(details.description)

            if (details.titles.isNotEmpty()){
                binding.titlesParent.visibility = View.VISIBLE
                binding.titlesTextView.text = details.titles.joinToString { it.title }
            }
            else{
                binding.titlesParent.visibility = View.GONE
            }

            if (details.names.isNotEmpty()){
                binding.peopleParent.visibility = View.VISIBLE
                binding.peopleTextView.text = details.names.joinToString { it.name }
            }
            else{
                binding.peopleParent.visibility = View.GONE
            }

            if (details.countries.isNotEmpty()){
                binding.countriesParent.visibility = View.VISIBLE
                binding.countriesTextView.text = details.countries.joinToString()
            }
            else{
                binding.countriesParent.visibility = View.GONE
            }

            if (details.languages.isNotEmpty()){
                binding.languagesParent.visibility = View.VISIBLE
                binding.languagesTextView.text = details.languages.joinToString()
            }
            else{
                binding.languagesParent.visibility = View.GONE
            }

            if (details.copyRight.isNotEmpty()){
                binding.copyRightParent.visibility = View.VISIBLE
                binding.copyRightTextView.text = details.copyRight
            }
            else{
                binding.copyRightParent.visibility = View.GONE
            }

            if (details.createdBy.isNotEmpty()){
                binding.createdByParent.visibility = View.VISIBLE
                binding.createdByTextView.text = details.createdBy
            }
            else{
                binding.createdByParent.visibility = View.GONE
            }
        }

    }

    private fun showError(message: String) {
        requireContext().showUnexpectedError()
        launchUseCase()
    }

    private fun showNetworkError() {
        requireContext().showNetworkConnectionError {
            launchUseCase()
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
        id = requireArguments().getString("id", "")
        imageId = requireArguments().getString("imageId", "")
        title = requireArguments().getString("title", "")
    }
}