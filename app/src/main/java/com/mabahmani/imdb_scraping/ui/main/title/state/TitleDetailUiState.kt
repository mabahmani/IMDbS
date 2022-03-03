package com.mabahmani.imdb_scraping.ui.main.title.state

import com.mabahmani.domain.vo.TitleDetails

sealed class TitleDetailUiState {
    object  Loading : TitleDetailUiState()
    object  NetworkError : TitleDetailUiState()
    object  TimeOutError : TitleDetailUiState()
    class   Error(val message: String) : TitleDetailUiState()
    class   ShowTitleDetails(val titleDetails: TitleDetails) : TitleDetailUiState()
}
