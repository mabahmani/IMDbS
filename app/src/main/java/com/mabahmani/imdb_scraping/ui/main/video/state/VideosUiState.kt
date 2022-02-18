package com.mabahmani.imdb_scraping.ui.main.video.state

import androidx.paging.PagingData
import com.mabahmani.domain.vo.common.Video
import kotlinx.coroutines.flow.Flow

sealed class VideosUiState {
    object  Loading : VideosUiState()
    class   ShowVideos(val pagingData: Flow<PagingData<Video>>) : VideosUiState()
}
