package com.mabahmani.imdb_scraping.ui.main.home.state

import com.mabahmani.domain.vo.Home
import com.mabahmani.domain.vo.common.Trailer

sealed class HomeUiState {
    object Loading : HomeUiState()
    object ShowNetworkError : HomeUiState()
    class ShowError(val message: String) : HomeUiState()
    class ShowData(val home: Home) : HomeUiState()
}
