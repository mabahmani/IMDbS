package com.mabahmani.imdb_scraping.ui.main.search.state

import com.mabahmani.domain.vo.common.Event

sealed class EventsUiState {
    object  Loading : EventsUiState()
    object  NetworkError : EventsUiState()
    object  TimeOutError : EventsUiState()
    class   Error(val message: String) : EventsUiState()
    class   ShowSearchData(val events: List<Event>) : EventsUiState()
}
