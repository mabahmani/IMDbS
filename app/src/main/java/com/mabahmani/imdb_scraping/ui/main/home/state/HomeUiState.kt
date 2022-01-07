package com.mabahmani.imdb_scraping.ui.main.home.state

import com.mabahmani.domain.vo.Home
import com.mabahmani.domain.vo.HomeExtra
import com.mabahmani.domain.vo.common.Trailer

sealed class HomeUiState {
    object  Loading : HomeUiState()
    object  NetworkError : HomeUiState()
    class   Error(val message: String) : HomeUiState()
    class   ShowHomeData(val home: Home) : HomeUiState()
}
