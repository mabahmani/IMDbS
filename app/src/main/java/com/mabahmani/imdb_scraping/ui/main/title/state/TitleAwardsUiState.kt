package com.mabahmani.imdb_scraping.ui.main.title.state

import com.mabahmani.domain.vo.TitleAwards

sealed class TitleAwardsUiState {
    object  Loading : TitleAwardsUiState()
    object  NetworkError : TitleAwardsUiState()
    object  TimeOutError : TitleAwardsUiState()
    class   Error(val message: String) : TitleAwardsUiState()
    class   ShowTitleAwards(val titleAwards: TitleAwards) : TitleAwardsUiState()
}
