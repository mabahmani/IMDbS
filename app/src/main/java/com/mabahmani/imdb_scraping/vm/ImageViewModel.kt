package com.mabahmani.imdb_scraping.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mabahmani.domain.interactor.GetListImagesUseCase
import com.mabahmani.domain.interactor.GetNameImagesUseCase
import com.mabahmani.domain.interactor.GetTitleImagesUseCase
import com.mabahmani.domain.vo.common.ListId
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.imdb_scraping.ui.main.image.state.ImagesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val getListImagesUseCase: GetListImagesUseCase,
    private val getTitleImagesUseCase: GetTitleImagesUseCase,
    private val getNameImagesUseCase: GetNameImagesUseCase,
) : ViewModel() {

    private val _imagesUiState = MutableStateFlow<ImagesUiState>(ImagesUiState.Loading)
    val imagesUiState: StateFlow<ImagesUiState> = _imagesUiState

    fun launchGetListImagesUseCase(listId: ListId) {
        if (_imagesUiState.value !is ImagesUiState.ShowImages) {

            viewModelScope.launch {

                _imagesUiState.emit(ImagesUiState.Loading)

                _imagesUiState.emit(
                    ImagesUiState.ShowImages(
                        getListImagesUseCase(listId).flow.cachedIn(viewModelScope)
                    )
                )

            }
        }
    }

    fun launchGetTitleImagesUseCase(titleId: TitleId) {
        if (_imagesUiState.value !is ImagesUiState.ShowImages) {

            viewModelScope.launch {

                _imagesUiState.emit(ImagesUiState.Loading)

                _imagesUiState.emit(
                    ImagesUiState.ShowImages(
                        getTitleImagesUseCase(titleId).flow.cachedIn(viewModelScope)
                    )
                )

            }
        }
    }

    fun launchGetNameImagesUseCase(nameId: NameId) {
        if (_imagesUiState.value !is ImagesUiState.ShowImages) {

            viewModelScope.launch {

                _imagesUiState.emit(ImagesUiState.Loading)

                _imagesUiState.emit(
                    ImagesUiState.ShowImages(
                        getNameImagesUseCase(nameId).flow.cachedIn(viewModelScope)
                    )
                )

            }
        }
    }
}