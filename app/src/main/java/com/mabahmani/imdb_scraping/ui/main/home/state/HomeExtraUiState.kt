package com.mabahmani.imdb_scraping.ui.main.home.state

import com.mabahmani.domain.vo.Home
import com.mabahmani.domain.vo.HomeExtra
import com.mabahmani.domain.vo.common.Trailer

sealed class HomeExtraUiState {
    object  Loading : HomeExtraUiState()
    object  NetworkError : HomeExtraUiState()
    class   Error(val message: String) : HomeExtraUiState()
    class   ShowHomeExtraData(val homeExtra: HomeExtra) : HomeExtraUiState()
}
