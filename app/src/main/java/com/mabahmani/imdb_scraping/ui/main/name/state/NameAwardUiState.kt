package com.mabahmani.imdb_scraping.ui.main.name.state

import com.mabahmani.domain.vo.NameAwards

sealed class NameAwardUiState {
    object  Loading : NameAwardUiState()
    object  NetworkError : NameAwardUiState()
    object  TimeOutError : NameAwardUiState()
    class   Error(val message: String) : NameAwardUiState()
    class   ShowNameAwards(val nameAwards: NameAwards) : NameAwardUiState()
}
