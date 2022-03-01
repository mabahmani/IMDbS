package com.mabahmani.imdb_scraping.ui.main.home.state

import com.mabahmani.domain.vo.HomeExtra

sealed class HomeExtraUiState {
    object  Loading : HomeExtraUiState()
    object  NetworkError : HomeExtraUiState()
    class   Error(val message: String) : HomeExtraUiState()
    class   ShowHomeExtraData(val homeExtra: HomeExtra) : HomeExtraUiState()
}
