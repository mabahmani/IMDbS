package com.mabahmani.imdb_scraping.ui.main.name.state

import com.mabahmani.domain.vo.NameBio

sealed class NameBioUiState {
    object  Loading : NameBioUiState()
    object  NetworkError : NameBioUiState()
    object  TimeOutError : NameBioUiState()
    class   Error(val message: String) : NameBioUiState()
    class   ShowNameBio(val nameBio: NameBio) : NameBioUiState()
}
