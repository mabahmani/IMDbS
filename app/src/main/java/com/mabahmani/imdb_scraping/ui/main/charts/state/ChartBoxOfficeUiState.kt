package com.mabahmani.imdb_scraping.ui.main.charts.state

import com.mabahmani.domain.vo.common.BoxOffice
import com.mabahmani.domain.vo.common.Title

sealed class ChartBoxOfficeUiState{
    object  Loading : ChartBoxOfficeUiState()
    object  NetworkError : ChartBoxOfficeUiState()
    class   Error(val message: String) : ChartBoxOfficeUiState()
    class   ShowBoxOfficeData(val boxOffice: BoxOffice) : ChartBoxOfficeUiState()
}
