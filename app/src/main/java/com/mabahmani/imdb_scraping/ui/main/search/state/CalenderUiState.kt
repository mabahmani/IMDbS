package com.mabahmani.imdb_scraping.ui.main.search.state

import com.mabahmani.domain.vo.Calender

sealed class CalenderUiState {
    object  Loading : CalenderUiState()
    object  NetworkError : CalenderUiState()
    object  TimeOutError : CalenderUiState()
    class   Error(val message: String) : CalenderUiState()
    class   ShowSearchData(val calenders: List<Calender>) : CalenderUiState()
}
