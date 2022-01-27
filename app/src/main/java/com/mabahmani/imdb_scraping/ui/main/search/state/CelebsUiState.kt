package com.mabahmani.imdb_scraping.ui.main.search.state

import androidx.paging.PagingData
import com.mabahmani.domain.vo.common.Name
import kotlinx.coroutines.flow.Flow

sealed class CelebsUiState {
    object  Loading : CelebsUiState()
    class   ShowSearchData(val pagingData: Flow<PagingData<Name>>) : CelebsUiState()
}
