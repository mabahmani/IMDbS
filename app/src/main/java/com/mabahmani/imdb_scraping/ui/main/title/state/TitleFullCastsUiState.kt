package com.mabahmani.imdb_scraping.ui.main.title.state

import com.mabahmani.domain.vo.TitleFullCasts

sealed class TitleFullCastsUiState {
    object  Loading : TitleFullCastsUiState()
    object  NetworkError : TitleFullCastsUiState()
    object  TimeOutError : TitleFullCastsUiState()
    class   Error(val message: String) : TitleFullCastsUiState()
    class   ShowTitleFullCasts(val titleFullCasts: TitleFullCasts) : TitleFullCastsUiState()
}
