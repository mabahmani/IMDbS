package com.mabahmani.imdb_scraping.ui.main.search.state

import com.mabahmani.domain.vo.common.Genre

sealed class GenresUiState {
    object  Loading : GenresUiState()
    object  NetworkError : GenresUiState()
    object  TimeOutError : GenresUiState()
    class   Error(val message: String) : GenresUiState()
    class   ShowSearchData(val genres: List<Genre>) : GenresUiState()
}
