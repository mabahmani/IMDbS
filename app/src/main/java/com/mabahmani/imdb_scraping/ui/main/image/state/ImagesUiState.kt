package com.mabahmani.imdb_scraping.ui.main.image.state

import androidx.paging.PagingData
import com.mabahmani.domain.vo.common.ImageLink
import kotlinx.coroutines.flow.Flow

sealed class ImagesUiState {
    object  Loading : ImagesUiState()
    class   ShowImages(val pagingData: Flow<PagingData<ImageLink>>) : ImagesUiState()
}
