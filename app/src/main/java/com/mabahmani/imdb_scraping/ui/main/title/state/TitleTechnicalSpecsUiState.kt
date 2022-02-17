package com.mabahmani.imdb_scraping.ui.main.title.state

import com.mabahmani.domain.vo.TitleTechnicalSpecs

sealed class TitleTechnicalSpecsUiState {
    object  Loading : TitleTechnicalSpecsUiState()
    object  NetworkError : TitleTechnicalSpecsUiState()
    class   Error(val message: String) : TitleTechnicalSpecsUiState()
    class   ShowTitleTechnicalSpecs(val titleTechnicalSpecs: TitleTechnicalSpecs) : TitleTechnicalSpecsUiState()
}
