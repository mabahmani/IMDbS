package com.mabahmani.imdb_scraping.ui.main.charts.state

import com.mabahmani.domain.vo.common.BoxOffice

sealed class ChartBoxOfficeUiState{
    object  Loading : ChartBoxOfficeUiState()
    object  NetworkError : ChartBoxOfficeUiState()
    object  TimeOutError : ChartBoxOfficeUiState()
    class   Error(val message: String) : ChartBoxOfficeUiState()
    class   ShowBoxOfficeData(val boxOffice: BoxOffice) : ChartBoxOfficeUiState()
}
