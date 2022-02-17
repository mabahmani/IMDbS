package com.mabahmani.imdb_scraping.ui.main.title.state

import com.mabahmani.domain.vo.TitleParentsGuide

sealed class TitleParentsGuideUiState {
    object  Loading : TitleParentsGuideUiState()
    object  NetworkError : TitleParentsGuideUiState()
    class   Error(val message: String) : TitleParentsGuideUiState()
    class   ShowTitleParentsGuide(val titleParentsGuide: TitleParentsGuide) : TitleParentsGuideUiState()
}
