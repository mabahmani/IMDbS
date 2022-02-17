package com.mabahmani.imdb_scraping.ui.main.image.state

import androidx.paging.PagingData
import com.mabahmani.domain.vo.Home
import com.mabahmani.domain.vo.HomeExtra
import com.mabahmani.domain.vo.NameBio
import com.mabahmani.domain.vo.NameDetails
import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.ImageLink
import com.mabahmani.domain.vo.common.Name
import com.mabahmani.domain.vo.common.Trailer
import kotlinx.coroutines.flow.Flow

sealed class ImagesUiState {
    object  Loading : ImagesUiState()
    class   ShowImages(val pagingData: Flow<PagingData<ImageLink>>) : ImagesUiState()
}
