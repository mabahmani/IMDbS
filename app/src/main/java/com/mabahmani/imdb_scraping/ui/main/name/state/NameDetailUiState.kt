package com.mabahmani.imdb_scraping.ui.main.name.state

import com.mabahmani.domain.vo.Home
import com.mabahmani.domain.vo.HomeExtra
import com.mabahmani.domain.vo.NameDetails
import com.mabahmani.domain.vo.common.Trailer

sealed class NameDetailUiState {
    object  Loading : NameDetailUiState()
    object  NetworkError : NameDetailUiState()
    class   Error(val message: String) : NameDetailUiState()
    class   ShowNameDetails(val nameDetails: NameDetails) : NameDetailUiState()
}
