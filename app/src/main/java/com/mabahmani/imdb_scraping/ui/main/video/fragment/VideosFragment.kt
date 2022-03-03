package com.mabahmani.imdb_scraping.ui.main.video.fragment

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
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.domain.vo.common.Video
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.FragmentVideosBinding
import com.mabahmani.imdb_scraping.ui.main.video.adapter.VideosAdapter
import com.mabahmani.imdb_scraping.ui.main.video.state.VideosUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.vm.VideoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@AndroidEntryPoint
class VideosFragment : Fragment() {

    lateinit var binding: FragmentVideosBinding

    private val viewModel: VideoViewModel by viewModels()
    private lateinit var adapter: VideosAdapter
    lateinit var id: String
    lateinit var title: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideosBinding.inflate(inflater, container, false)
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

        adapter = VideosAdapter {
            findNavController().navigate(R.id.videoDetailsFragment,
                Bundle().apply {
                    putString("videoId", it?.videoId?.value)
                    putString("title", it?.title)
                }
            )
        }

        binding.list.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.list.adapter = adapter
    }

    private fun initImagesStateObserver() {

        launchUseCase()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.videosUiState.collect {
                    handleVideosStates(it)
                }
            }
        }
    }

    private fun launchUseCase() {
        when {
            id.startsWith("tt") -> {
                viewModel.launchGetTitleVideosUseCase(TitleId(id))
            }
            id.startsWith("nm") -> {
                viewModel.launchGetNameVideosUseCase(NameId(id))
            }
        }
    }

    private fun handleVideosStates(state: VideosUiState) {
        when (state) {
            is VideosUiState.Loading -> {
                showLoading()
            }
            is VideosUiState.ShowVideos -> {
                showVideos(state.pagingData)
            }
        }
    }

    private fun showVideos(videos: Flow<PagingData<Video>>) {

        viewLifecycleOwner.lifecycleScope.launch {
            videos.collectLatest {
                adapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
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
                        when ((it.refresh as LoadState.Error).error) {
                            is UnknownHostException -> showNetworkError()
                            is SocketTimeoutException -> retry()
                            else -> showError(error.message.orEmpty())
                        }
                    }
                }

                if (it.append is LoadState.Error) {
                    val error = (it.append as LoadState.Error).error
                    when ((it.append as LoadState.Error).error) {
                        is UnknownHostException -> showNetworkError()
                        is SocketTimeoutException -> retry()
                        else -> showError(error.message.orEmpty())
                    }
                }

            }
        }

    }

    private fun retry() {
        adapter.retry()
    }

    private fun showNetworkError() {
        requireContext().showNetworkConnectionError {
            adapter.retry()
        }
    }

    private fun showError(message: String) {
        requireContext().showUnexpectedError(message)
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