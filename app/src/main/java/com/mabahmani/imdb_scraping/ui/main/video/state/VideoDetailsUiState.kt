package com.mabahmani.imdb_scraping.ui.main.video.state

import com.mabahmani.domain.vo.VideoDetails

sealed class VideoDetailsUiState {
    object  Loading : VideoDetailsUiState()
    object  NetworkError : VideoDetailsUiState()
    class   Error(val message: String) : VideoDetailsUiState()
    class   ShowVideoDetails(val videoDetails: VideoDetails) : VideoDetailsUiState()
}
