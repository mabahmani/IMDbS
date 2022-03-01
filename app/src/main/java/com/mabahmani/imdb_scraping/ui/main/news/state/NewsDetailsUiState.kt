package com.mabahmani.imdb_scraping.ui.main.news.state

import com.mabahmani.domain.vo.NewsDetails

sealed class NewsDetailsUiState {
    object  Loading : NewsDetailsUiState()
    object  NetworkError : NewsDetailsUiState()
    class   Error(val message: String) : NewsDetailsUiState()
    class   ShowNewsDetails(val newsDetails: NewsDetails) : NewsDetailsUiState()
}
