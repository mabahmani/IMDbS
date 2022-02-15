package com.mabahmani.imdb_scraping.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mabahmani.domain.interactor.GetTitleDetailsUseCase
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.imdb_scraping.ui.main.title.state.TitleDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class TitleViewModel @Inject constructor(
    private val getTitleDetailsUseCase: GetTitleDetailsUseCase
) : ViewModel() {
    private val _titleDetailsUiState = MutableStateFlow<TitleDetailUiState>(TitleDetailUiState.Loading)
    val titleDetailsUiState: StateFlow<TitleDetailUiState> = _titleDetailsUiState

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

}