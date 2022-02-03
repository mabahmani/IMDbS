package com.mabahmani.imdb_scraping.ui.main.charts.state

import com.mabahmani.domain.vo.common.Title

sealed class ChartUiState{
    object  Loading : ChartUiState()
    object  NetworkError : ChartUiState()
    class   Error(val message: String) : ChartUiState()
    class   ShowChartsData(val titles: List<Title>) : ChartUiState()
}
