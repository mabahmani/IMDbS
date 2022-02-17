package com.mabahmani.imdb_scraping.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mabahmani.domain.interactor.GetTitleAwardsUseCase
import com.mabahmani.domain.interactor.GetTitleDetailsUseCase
import com.mabahmani.domain.interactor.GetTitleFullCastsUseCase
import com.mabahmani.domain.interactor.GetTitleParentsGuideUseCase
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.imdb_scraping.ui.main.title.state.TitleAwardsUiState
import com.mabahmani.imdb_scraping.ui.main.title.state.TitleDetailUiState
import com.mabahmani.imdb_scraping.ui.main.title.state.TitleFullCastsUiState
import com.mabahmani.imdb_scraping.ui.main.title.state.TitleParentsGuideUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class TitleViewModel @Inject constructor(
    private val getTitleDetailsUseCase: GetTitleDetailsUseCase,
    private val getTitleAwardsUseCase: GetTitleAwardsUseCase,
    private val getTitleFullCastsUseCase: GetTitleFullCastsUseCase,
    private val getTitleParentsGuideUseCase: GetTitleParentsGuideUseCase,
) : ViewModel() {

    private val _titleDetailsUiState = MutableStateFlow<TitleDetailUiState>(TitleDetailUiState.Loading)
    val titleDetailsUiState: StateFlow<TitleDetailUiState> = _titleDetailsUiState

    private val _titleAwardsUiState = MutableStateFlow<TitleAwardsUiState>(TitleAwardsUiState.Loading)
    val titleAwardsUiState: StateFlow<TitleAwardsUiState> = _titleAwardsUiState

    private val _titleFullCastsUiState = MutableStateFlow<TitleFullCastsUiState>(TitleFullCastsUiState.Loading)
    val titleFullCastsUiState: StateFlow<TitleFullCastsUiState> = _titleFullCastsUiState

    private val _titleParentsGuideUiState = MutableStateFlow<TitleParentsGuideUiState>(TitleParentsGuideUiState.Loading)
    val titleParentsGuideUiState: StateFlow<TitleParentsGuideUiState> = _titleParentsGuideUiState

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

                            else -> {
                                _titleParentsGuideUiState.emit(TitleParentsGuideUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }

    }

}