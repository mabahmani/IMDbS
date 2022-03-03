package com.mabahmani.imdb_scraping.ui.main.event.state

import com.mabahmani.domain.vo.EventDetails

sealed class EventDetailsUiState {
    object  Loading : EventDetailsUiState()
    object  NetworkError : EventDetailsUiState()
    object  TimeOutError : EventDetailsUiState()
    class   Error(val message: String) : EventDetailsUiState()
    class   ShowEventDetailsData(val eventDetails: EventDetails) : EventDetailsUiState()
}
