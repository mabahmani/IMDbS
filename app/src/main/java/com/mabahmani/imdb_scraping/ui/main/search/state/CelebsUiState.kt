package com.mabahmani.imdb_scraping.ui.main.search.state

import com.mabahmani.domain.vo.common.Name

sealed class CelebsUiState {
    object  Loading : CelebsUiState()
    object  NetworkError : CelebsUiState()
    class   Error(val message: String) : CelebsUiState()
    class   ShowSearchData(val celebs: List<Name>) : CelebsUiState()
}
