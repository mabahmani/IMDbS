package com.mabahmani.imdb_scraping.ui.main.search.state

import androidx.paging.PagingData
import com.mabahmani.domain.vo.common.Title
import kotlinx.coroutines.flow.Flow

sealed class TitlesUiState {
    object  Loading : TitlesUiState()
    class   ShowSearchData(val pagingData: Flow<PagingData<Title>>) : TitlesUiState()
}
