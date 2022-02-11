package com.mabahmani.imdb_scraping.ui.main.name.state

import com.mabahmani.domain.vo.Home
import com.mabahmani.domain.vo.HomeExtra
import com.mabahmani.domain.vo.NameBio
import com.mabahmani.domain.vo.NameDetails
import com.mabahmani.domain.vo.common.Trailer

sealed class NameBioUiState {
    object  Loading : NameBioUiState()
    object  NetworkError : NameBioUiState()
    class   Error(val message: String) : NameBioUiState()
    class   ShowNameBio(val nameBio: NameBio) : NameBioUiState()
}
