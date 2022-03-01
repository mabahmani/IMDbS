package com.mabahmani.imdb_scraping.ui.main.image.fragment

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
import androidx.recyclerview.widget.GridLayoutManager
import com.mabahmani.domain.vo.common.*
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.FragmentImagesBinding
import com.mabahmani.imdb_scraping.ui.main.image.adapter.ImagesAdapter
import com.mabahmani.imdb_scraping.ui.main.image.state.ImagesUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.vm.ImageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.net.UnknownHostException

@AndroidEntryPoint
class ImagesFragment : Fragment (){

    lateinit var binding: FragmentImagesBinding

    private val viewModel: ImageViewModel by viewModels()
    private lateinit var adapter: ImagesAdapter
    lateinit var id: String
    lateinit var title: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkArguments()
        setupAppBar()
        setupList()
        initImagesStateObserver()

    }

    private fun setupAppBar() {
        binding.appBar.getTitleView()?.text = title
    }

    private fun checkArguments() {
        id = requireArguments().getString("id", "")
        title = requireArguments().getString("title", "")
    }

    private fun setupList() {

        adapter = ImagesAdapter{
            findNavController().navigate(R.id.imageDetailsFragment,
                    Bundle().apply {
                        putString("id", id)
                        putString("imageId", it?.imageId?.value)
                        putString("title", title)
                    }
                )
        }

        binding.list.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.list.adapter = adapter
    }

    private fun initImagesStateObserver() {

        launchUseCase()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.imagesUiState.collect {
                    handleImagesStates(it)
                }
            }
        }
    }

    private fun launchUseCase() {
        when {
            id.startsWith("tt") -> {
                viewModel.launchGetTitleImagesUseCase(TitleId(id))
            }
            id.startsWith("nm") -> {
                viewModel.launchGetNameImagesUseCase(NameId(id))
            }
            id.startsWith("rg") -> {
                viewModel.launchGetGalleryImagesUseCase(GalleryId(id))
            }
            else -> {
                viewModel.launchGetListImagesUseCase(ListId(id))
            }
        }
    }

    private fun handleImagesStates(state: ImagesUiState) {
        when (state) {
            is ImagesUiState.Loading -> {
                showLoading()
            }
            is ImagesUiState.ShowImages -> {
                showImages(state.pagingData)
            }
        }
    }

    private fun showImages(images: Flow<PagingData<ImageLink>>) {

        viewLifecycleOwner.lifecycleScope.launch {
            images.collectLatest {
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
//        requireContext().showUnexpectedError()
//        adapter.retry()
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