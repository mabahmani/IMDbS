package com.mabahmani.imdb_scraping.ui.main.trailers.state

import com.mabahmani.domain.vo.common.Trailer

sealed class TrailersUiState{
    object  Loading : TrailersUiState()
    object  NetworkError : TrailersUiState()
    object  TimeOutError : TrailersUiState()
    class   Error(val message: String) : TrailersUiState()
    class   ShowTrailersData(val trailers: List<Trailer>) : TrailersUiState()
}
