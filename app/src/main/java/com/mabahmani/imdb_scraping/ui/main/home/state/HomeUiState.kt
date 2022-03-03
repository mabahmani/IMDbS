package com.mabahmani.imdb_scraping.ui.main.home.state

import com.mabahmani.domain.vo.Home

sealed class HomeUiState {
    object  Loading : HomeUiState()
    object  NetworkError : HomeUiState()
    object  TimeOutError : HomeUiState()
    class   Error(val message: String) : HomeUiState()
    class   ShowHomeData(val home: Home) : HomeUiState()
}
