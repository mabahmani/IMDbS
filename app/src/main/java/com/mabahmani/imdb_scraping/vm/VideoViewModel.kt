package com.mabahmani.imdb_scraping.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mabahmani.domain.interactor.*
import com.mabahmani.domain.vo.common.*
import com.mabahmani.imdb_scraping.ui.main.image.state.ImageDetailsUiState
import com.mabahmani.imdb_scraping.ui.main.image.state.ImagesUiState
import com.mabahmani.imdb_scraping.ui.main.name.state.NameDetailUiState
import com.mabahmani.imdb_scraping.ui.main.video.state.VideoDetailsUiState
import com.mabahmani.imdb_scraping.ui.main.video.state.VideosUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val getTitleVideosUseCase: GetTitleVideosUseCase,
    private val getNameVideosUseCase: GetNameVideosUseCase,
    private val getVideoDetailsUseCase: GetVideoDetailsUseCase
) : ViewModel() {

    private val _videosUiState = MutableStateFlow<VideosUiState>(VideosUiState.Loading)
    val videosUiState: StateFlow<VideosUiState> = _videosUiState

    private val _videoDetailsUiState = MutableStateFlow<VideoDetailsUiState>(VideoDetailsUiState.Loading)
    val videoDetailsUiState: StateFlow<VideoDetailsUiState> = _videoDetailsUiState

    fun launchGetTitleVideosUseCase(titleId: TitleId) {
        if (_videosUiState.value !is VideosUiState.ShowVideos) {

            viewModelScope.launch {

                _videosUiState.emit(VideosUiState.Loading)

                _videosUiState.emit(
                    VideosUiState.ShowVideos(
                        getTitleVideosUseCase(titleId).flow.cachedIn(viewModelScope)
                    )
                )

            }
        }
    }

    fun launchGetNameVideosUseCase(nameId: NameId) {
        if (_videosUiState.value !is VideosUiState.ShowVideos) {

            viewModelScope.launch {

                _videosUiState.emit(VideosUiState.Loading)

                _videosUiState.emit(
                    VideosUiState.ShowVideos(
                        getNameVideosUseCase(nameId).flow.cachedIn(viewModelScope)
                    )
                )

            }
        }
    }

    fun launchGetVideoDetailsUseCase(videoId: VideoId) {
        if (_videoDetailsUiState.value !is VideoDetailsUiState.ShowVideoDetails) {

            viewModelScope.launch {

                _videoDetailsUiState.emit(VideoDetailsUiState.Loading)

                val videoDetails = getVideoDetailsUseCase(videoId)

                if (videoDetails.isSuccess) {
                    videoDetails.getOrNull()?.let {
                        _videoDetailsUiState.emit(VideoDetailsUiState.ShowVideoDetails(it))
                    }
                } else {
                    videoDetails.exceptionOrNull()?.let {
                        when (it) {
                            is UnknownHostException -> {
                                _videoDetailsUiState.emit(VideoDetailsUiState.NetworkError)
                            }

                            else -> {
                                _videoDetailsUiState.emit(VideoDetailsUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }
    }

}