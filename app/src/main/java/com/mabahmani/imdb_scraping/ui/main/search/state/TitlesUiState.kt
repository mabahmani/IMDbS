package com.mabahmani.imdb_scraping.ui.main.search.state

import com.mabahmani.domain.vo.common.Title

sealed class TitlesUiState {
    object  Loading : TitlesUiState()
    object  NetworkError : TitlesUiState()
    class   Error(val message: String) : TitlesUiState()
    class   ShowSearchData(val titles: List<Title>) : TitlesUiState()
}
