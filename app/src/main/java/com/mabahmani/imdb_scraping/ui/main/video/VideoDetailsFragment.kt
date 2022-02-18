package com.mabahmani.imdb_scraping.ui.main.video

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.mabahmani.domain.vo.VideoDetails
import com.mabahmani.domain.vo.common.VideoId
import com.mabahmani.imdb_scraping.databinding.FragmentVideoDetailsBinding
import com.mabahmani.imdb_scraping.ui.main.video.state.VideoDetailsUiState
import com.mabahmani.imdb_scraping.util.orZero
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.vm.VideoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideoDetailsFragment: Fragment() {

    lateinit var binding: FragmentVideoDetailsBinding
    private val viewModel: VideoViewModel by viewModels()

    lateinit var videoId: String
    lateinit var title: String

    private var player: SimpleExoPlayer? = null

    private var playerProgressIsTracking = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkArguments()
        setupAppBar()
        setOnClickListeners()
        observeVideoDetailsUiState()
    }

    private fun setOnClickListeners() {
        binding.playerCover.setOnClickListener {
            player?.prepare()
            player?.play()
            binding.playerCover.visibility = View.GONE
            binding.playerIcon.visibility = View.GONE
        }

        binding.playerOverLayer.setOnClickListener {
            if (player?.isPlaying == true){
                player?.pause()
                binding.playerIcon.visibility = View.VISIBLE
            }

            else{
                player?.play()
                binding.playerIcon.visibility = View.GONE
            }
        }

        binding.playerForward.setOnClickListener {
            player?.seekTo(player?.currentPosition?.plus(10000) ?: 0)
        }

        binding.playerBackward.setOnClickListener {
            player?.seekTo(player?.currentPosition?.minus(10000) ?: 0)
        }
    }

    private fun setupAppBar() {
        binding.appBar.getTitleView()?.text = title
    }

    private fun observeVideoDetailsUiState() {

        viewModel.launchGetVideoDetailsUseCase(VideoId(videoId))

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.videoDetailsUiState.collect {
                    handleVideoDetailsStates(it)
                }
            }
        }
    }

    private fun handleVideoDetailsStates(state: VideoDetailsUiState) {
        when (state) {
            is VideoDetailsUiState.Loading -> {
                showLoading()
            }
            is VideoDetailsUiState.ShowVideoDetails -> {
                showVideoDetails(state.videoDetails)
                hideLoading()
            }
            is VideoDetailsUiState.Error -> {
                showError(state.message)
            }
            is VideoDetailsUiState.NetworkError -> {
                showNetworkError()
            }
        }
    }

    private fun showVideoDetails(videoDetails: VideoDetails) {
        binding.caption = videoDetails.caption
        binding.titleName = videoDetails.relatedTitle.title
        binding.titleDate = videoDetails.relatedTitle.releaseYear
        binding.titleCategory = videoDetails.relatedTitle.genres
        binding.titleRate = videoDetails.relatedTitle.rate
        binding.titleCoverUrl = videoDetails.relatedTitle.cover?.getCustomImageWidthUrl(512)
        binding.videoPreviewImageUrl = videoDetails.preview.getCustomImageWidthUrl(512)

        val adapter = VideoDetailsRelatedVideosAdapter{

        }

        binding.relatedVideosList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.relatedVideosList.adapter = adapter

        adapter.submitList(videoDetails.relatedVideos)

        setupVideoPlayer(videoDetails.highQualityUrl)
    }

    private fun setupVideoPlayer(highQualityUrl: String) {

        val mediaItem = MediaItem.fromUri(highQualityUrl)
        player?.setMediaItem(mediaItem)
    }

    private fun showError(message: String) {
        requireContext().showUnexpectedError()
        viewModel.launchGetVideoDetailsUseCase(VideoId(videoId))
    }

    private fun showNetworkError() {
        requireContext().showNetworkConnectionError {
            viewModel.launchGetVideoDetailsUseCase(VideoId(videoId))
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
        videoId = requireArguments().getString("videoId", "")
        title = requireArguments().getString("title", "")
    }

    private fun initializePlayer() {
        player = SimpleExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                binding.playerView.player = exoPlayer
            }


        player?.addListener(object : Player.Listener{
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                if (playbackState == ExoPlayer.STATE_BUFFERING){
                    binding.playerLoading.visibility = View.VISIBLE;
                } else {
                    binding.playerLoading.visibility = View.GONE;
                }
            }
        })

        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                binding.playerSeekbar.max = (player?.duration ?: 0).toInt()
                binding.playerSeekbar.progress = player?.currentPosition?.toFloat().orZero().toInt()
                mainHandler.postDelayed(this, 1000)
            }
        })

        binding.playerSeekbar.setOnSeekBarChangeListener(object  : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser && !playerProgressIsTracking)
                    player?.seekTo(progress.toLong())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                playerProgressIsTracking = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                playerProgressIsTracking = false
                player?.seekTo(seekBar?.progress?.toLong() ?: 0)
            }

        })
    }

    private fun releasePlayer() {
        player?.run {
            playWhenReady = this.playWhenReady
            release()
        }
        player = null
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }
    override fun onResume() {
        super.onResume()
        initializePlayer()
    }
    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }
}