package com.mabahmani.imdb_scraping.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mabahmani.domain.interactor.*
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.imdb_scraping.ui.main.search.state.SuggestionsUiState
import com.mabahmani.imdb_scraping.ui.main.title.state.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class TitleViewModel @Inject constructor(
    private val getTitleDetailsUseCase: GetTitleDetailsUseCase,
    private val getTitleAwardsUseCase: GetTitleAwardsUseCase,
    private val getTitleFullCastsUseCase: GetTitleFullCastsUseCase,
    private val getTitleParentsGuideUseCase: GetTitleParentsGuideUseCase,
    private val getTitleTechnicalSpecsUseCase: GetTitleTechnicalSpecsUseCase,
) : ViewModel() {

    private val _titleDetailsUiState = MutableStateFlow<TitleDetailUiState>(TitleDetailUiState.Loading)
    val titleDetailsUiState: StateFlow<TitleDetailUiState> = _titleDetailsUiState

    private val _titleAwardsUiState = MutableStateFlow<TitleAwardsUiState>(TitleAwardsUiState.Loading)
    val titleAwardsUiState: StateFlow<TitleAwardsUiState> = _titleAwardsUiState

    private val _titleFullCastsUiState = MutableStateFlow<TitleFullCastsUiState>(TitleFullCastsUiState.Loading)
    val titleFullCastsUiState: StateFlow<TitleFullCastsUiState> = _titleFullCastsUiState

    private val _titleParentsGuideUiState = MutableStateFlow<TitleParentsGuideUiState>(TitleParentsGuideUiState.Loading)
    val titleParentsGuideUiState: StateFlow<TitleParentsGuideUiState> = _titleParentsGuideUiState

    private val _titleTechnicalSpecsUiState = MutableStateFlow<TitleTechnicalSpecsUiState>(TitleTechnicalSpecsUiState.Loading)
    val titleTechnicalSpecsUiState: StateFlow<TitleTechnicalSpecsUiState> = _titleTechnicalSpecsUiState

    fun launchGetTitleDetailsUseCase(titleId: TitleId) {
        if (_titleDetailsUiState.value !is TitleDetailUiState.ShowTitleDetails) {

            viewModelScope.launch {

                _titleDetailsUiState.emit(TitleDetailUiState.Loading)

                val titleDetails = getTitleDetailsUseCase(titleId)

                if (titleDetails.isSuccess) {
                    titleDetails.getOrNull()?.let {
                        _titleDetailsUiState.emit(TitleDetailUiState.ShowTitleDetails(it))
                    }
                } else {
                    titleDetails.exceptionOrNull()?.let {
                        when (it) {
                            is UnknownHostException -> {
                                _titleDetailsUiState.emit(TitleDetailUiState.NetworkError)
                            }

                            is SocketTimeoutException ->{
                                _titleDetailsUiState.emit(TitleDetailUiState.TimeOutError)
                            }

                            else -> {
                                _titleDetailsUiState.emit(TitleDetailUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }

    }

    fun launchGetTitleAwardsUseCase(titleId: TitleId) {
        if (_titleAwardsUiState.value !is TitleAwardsUiState.ShowTitleAwards) {

            viewModelScope.launch {

                _titleAwardsUiState.emit(TitleAwardsUiState.Loading)

                val titleAwards = getTitleAwardsUseCase(titleId)

                if (titleAwards.isSuccess) {
                    titleAwards.getOrNull()?.let {
                        _titleAwardsUiState.emit(TitleAwardsUiState.ShowTitleAwards(it))
                    }
                } else {
                    titleAwards.exceptionOrNull()?.let {
                        when (it) {
                            is UnknownHostException -> {
                                _titleAwardsUiState.emit(TitleAwardsUiState.NetworkError)
                            }

                            is SocketTimeoutException ->{
                                _titleAwardsUiState.emit(TitleAwardsUiState.TimeOutError)
                            }

                            else -> {
                                _titleAwardsUiState.emit(TitleAwardsUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }

    }

    fun launchGetTitleFullCastsUseCase(titleId: TitleId) {
        if (_titleFullCastsUiState.value !is TitleFullCastsUiState.ShowTitleFullCasts) {

            viewModelScope.launch {

                _titleFullCastsUiState.emit(TitleFullCastsUiState.Loading)

                val titleFullCasts = getTitleFullCastsUseCase(titleId)

                if (titleFullCasts.isSuccess) {
                    titleFullCasts.getOrNull()?.let {
                        _titleFullCastsUiState.emit(TitleFullCastsUiState.ShowTitleFullCasts(it))
                    }
                } else {
                    titleFullCasts.exceptionOrNull()?.let {
                        when (it) {
                            is UnknownHostException -> {
                                _titleFullCastsUiState.emit(TitleFullCastsUiState.NetworkError)
                            }

                            is SocketTimeoutException ->{
                                _titleFullCastsUiState.emit(TitleFullCastsUiState.TimeOutError)
                            }

                            else -> {
                                _titleFullCastsUiState.emit(TitleFullCastsUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }

    }

    fun launchGetTitleParentsGuideUseCase(titleId: TitleId) {
        if (_titleParentsGuideUiState.value !is TitleParentsGuideUiState.ShowTitleParentsGuide) {

            viewModelScope.launch {

                _titleParentsGuideUiState.emit(TitleParentsGuideUiState.Loading)

                val titleParentsGuide = getTitleParentsGuideUseCase(titleId)

                if (titleParentsGuide.isSuccess) {
                    titleParentsGuide.getOrNull()?.let {
                        _titleParentsGuideUiState.emit(TitleParentsGuideUiState.ShowTitleParentsGuide(it))
                    }
                } else {
                    titleParentsGuide.exceptionOrNull()?.let {
                        when (it) {
                            is UnknownHostException -> {
                                _titleParentsGuideUiState.emit(TitleParentsGuideUiState.NetworkError)
                            }

                            is SocketTimeoutException ->{
                                _titleParentsGuideUiState.emit(TitleParentsGuideUiState.TimeOutError)
                            }

                            else -> {
                                _titleParentsGuideUiState.emit(TitleParentsGuideUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }

    }

    fun launchGetTitleTechnicalSpecsUseCase(titleId: TitleId) {
        if (_titleTechnicalSpecsUiState.value !is TitleTechnicalSpecsUiState.ShowTitleTechnicalSpecs) {

            viewModelScope.launch {

                _titleTechnicalSpecsUiState.emit(TitleTechnicalSpecsUiState.Loading)

                val titleTechnicalSpecs = getTitleTechnicalSpecsUseCase(titleId)

                if (titleTechnicalSpecs.isSuccess) {
                    titleTechnicalSpecs.getOrNull()?.let {
                        _titleTechnicalSpecsUiState.emit(TitleTechnicalSpecsUiState.ShowTitleTechnicalSpecs(it))
                    }
                } else {
                    titleTechnicalSpecs.exceptionOrNull()?.let {
                        when (it) {
                            is UnknownHostException -> {
                                _titleTechnicalSpecsUiState.emit(TitleTechnicalSpecsUiState.NetworkError)
                            }

                            is SocketTimeoutException ->{
                                _titleTechnicalSpecsUiState.emit(TitleTechnicalSpecsUiState.TimeOutError)
                            }

                            else -> {
                                _titleTechnicalSpecsUiState.emit(TitleTechnicalSpecsUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }

    }

}