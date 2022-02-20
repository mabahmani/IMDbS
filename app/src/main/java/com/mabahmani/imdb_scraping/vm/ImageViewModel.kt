package com.mabahmani.imdb_scraping.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mabahmani.domain.interactor.*
import com.mabahmani.domain.vo.common.*
import com.mabahmani.imdb_scraping.ui.main.image.state.ImageDetailsUiState
import com.mabahmani.imdb_scraping.ui.main.image.state.ImagesUiState
import com.mabahmani.imdb_scraping.ui.main.name.state.NameDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val getListImagesUseCase: GetListImagesUseCase,
    private val getTitleImagesUseCase: GetTitleImagesUseCase,
    private val getNameImagesUseCase: GetNameImagesUseCase,
    private val getGalleryImagesUseCase: GetGalleryImagesUseCase,
    private val getListImageDetailsUseCase: GetListImageDetailsUseCase,
    private val getTitleImageDetailsUseCase: GetTitleImageDetailsUseCase,
    private val getNameImageDetailsUseCase: GetNameImageDetailsUseCase,
    private val getGalleryImageDetailsUseCase: GetGalleryImageDetailsUseCase,
) : ViewModel() {

    private val _imagesUiState = MutableStateFlow<ImagesUiState>(ImagesUiState.Loading)
    val imagesUiState: StateFlow<ImagesUiState> = _imagesUiState

    private val _imagesDetailsUiState = MutableStateFlow<ImageDetailsUiState>(ImageDetailsUiState.Loading)
    val imagesDetailsUiState: StateFlow<ImageDetailsUiState> = _imagesDetailsUiState

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

    fun launchGetGalleryImagesUseCase(galleryId: GalleryId) {
        if (_imagesUiState.value !is ImagesUiState.ShowImages) {

            viewModelScope.launch {

                _imagesUiState.emit(ImagesUiState.Loading)

                _imagesUiState.emit(
                    ImagesUiState.ShowImages(
                        getGalleryImagesUseCase(galleryId).flow.cachedIn(viewModelScope)
                    )
                )

            }
        }
    }

    fun launchGetListImageDetailsUseCase(listId: ListId, imageId: ImageId){
        if (_imagesDetailsUiState.value !is ImageDetailsUiState.ShowImageDetails){

            viewModelScope.launch {

                _imagesDetailsUiState.emit(ImageDetailsUiState.Loading)

                val imageDetails = getListImageDetailsUseCase(
                    imageId = imageId,
                    listId = listId,
                    numberOfFirstImages = 1,
                    numberOfLastImages = 1
                )

                if (imageDetails.isSuccess){
                    imageDetails.getOrNull()?.let{
                        _imagesDetailsUiState.emit(ImageDetailsUiState.ShowImageDetails(it))
                    }
                }

                else{
                    imageDetails.exceptionOrNull()?.let{
                        when(it){
                            is UnknownHostException ->{
                                _imagesDetailsUiState.emit(ImageDetailsUiState.NetworkError)
                            }

                            else ->{
                                _imagesDetailsUiState.emit(ImageDetailsUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }

    }

    fun launchGetTitleImageDetailsUseCase(titleId: TitleId, imageId: ImageId){
        if (_imagesDetailsUiState.value !is ImageDetailsUiState.ShowImageDetails){

            viewModelScope.launch {

                _imagesDetailsUiState.emit(ImageDetailsUiState.Loading)

                val imageDetails = getTitleImageDetailsUseCase(
                    imageId = imageId,
                    titleId = titleId,
                    numberOfFirstImages = 1,
                    numberOfLastImages = 1
                )

                if (imageDetails.isSuccess){
                    imageDetails.getOrNull()?.let{
                        _imagesDetailsUiState.emit(ImageDetailsUiState.ShowImageDetails(it))
                    }
                }

                else{
                    imageDetails.exceptionOrNull()?.let{
                        when(it){
                            is UnknownHostException ->{
                                _imagesDetailsUiState.emit(ImageDetailsUiState.NetworkError)
                            }

                            else ->{
                                _imagesDetailsUiState.emit(ImageDetailsUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }

    }

    fun launchGetNameImageDetailsUseCase(nameId: NameId, imageId: ImageId){
        if (_imagesDetailsUiState.value !is ImageDetailsUiState.ShowImageDetails){

            viewModelScope.launch {

                _imagesDetailsUiState.emit(ImageDetailsUiState.Loading)

                val imageDetails = getNameImageDetailsUseCase(
                    imageId = imageId,
                    nameId = nameId,
                    numberOfFirstImages = 1,
                    numberOfLastImages = 1
                )

                if (imageDetails.isSuccess){
                    imageDetails.getOrNull()?.let{
                        _imagesDetailsUiState.emit(ImageDetailsUiState.ShowImageDetails(it))
                    }
                }

                else{
                    imageDetails.exceptionOrNull()?.let{
                        when(it){
                            is UnknownHostException ->{
                                _imagesDetailsUiState.emit(ImageDetailsUiState.NetworkError)
                            }

                            else ->{
                                _imagesDetailsUiState.emit(ImageDetailsUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }

    }

    fun launchGetGalleryImageDetailsUseCase(galleryId: GalleryId, imageId: ImageId){
        if (_imagesDetailsUiState.value !is ImageDetailsUiState.ShowImageDetails){

            viewModelScope.launch {

                _imagesDetailsUiState.emit(ImageDetailsUiState.Loading)

                val imageDetails = getGalleryImageDetailsUseCase(
                    imageId = imageId,
                    galleryId = galleryId,
                    numberOfFirstImages = 1,
                    numberOfLastImages = 1
                )

                if (imageDetails.isSuccess){
                    imageDetails.getOrNull()?.let{
                        _imagesDetailsUiState.emit(ImageDetailsUiState.ShowImageDetails(it))
                    }
                }

                else{
                    imageDetails.exceptionOrNull()?.let{
                        when(it){
                            is UnknownHostException ->{
                                _imagesDetailsUiState.emit(ImageDetailsUiState.NetworkError)
                            }

                            else ->{
                                _imagesDetailsUiState.emit(ImageDetailsUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }

    }
}