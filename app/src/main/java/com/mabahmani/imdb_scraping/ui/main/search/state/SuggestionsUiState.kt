package com.mabahmani.imdb_scraping.ui.main.search.state

import com.mabahmani.domain.vo.Suggestion

sealed class SuggestionsUiState {
    object  Idle : SuggestionsUiState()
    object  Loading : SuggestionsUiState()
    object  NetworkError : SuggestionsUiState()
    object  TimeOutError : SuggestionsUiState()
    class   Error(val message: String) : SuggestionsUiState()
    class   ShowSearchData(val suggestions: List<Suggestion>) : SuggestionsUiState()
}
