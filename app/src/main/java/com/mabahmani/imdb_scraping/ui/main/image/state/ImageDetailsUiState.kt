package com.mabahmani.imdb_scraping.ui.main.image.state

import com.mabahmani.domain.vo.*
import com.mabahmani.domain.vo.common.Trailer

sealed class ImageDetailsUiState {
    object  Loading : ImageDetailsUiState()
    object  NetworkError : ImageDetailsUiState()
    class   Error(val message: String) : ImageDetailsUiState()
    class   ShowImageDetails(val imageDetails: ImageDetails) : ImageDetailsUiState()
}
