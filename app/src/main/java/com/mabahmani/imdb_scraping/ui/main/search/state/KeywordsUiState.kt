package com.mabahmani.imdb_scraping.ui.main.search.state

import com.mabahmani.domain.vo.common.Title

sealed class KeywordsUiState {
    object  Loading : KeywordsUiState()
    object  NetworkError : KeywordsUiState()
    class   Error(val message: String) : KeywordsUiState()
    class   ShowSearchData(val keywords: List<String>) : KeywordsUiState()
}
