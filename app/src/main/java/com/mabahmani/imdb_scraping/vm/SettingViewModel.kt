package com.mabahmani.imdb_scraping.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mabahmani.domain.interactor.GetThemeModeUseCase
import com.mabahmani.domain.interactor.SetThemeModeUseCase
import com.mabahmani.imdb_scraping.ui.main.home.state.SettingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getThemeModeUseCase: GetThemeModeUseCase,
    private val setThemeModeUseCase: SetThemeModeUseCase
) : ViewModel() {

    private val _settingUiState = MutableStateFlow<SettingUiState>(SettingUiState.Loading)
    val settingUiState: StateFlow<SettingUiState> = _settingUiState

    init {
        getThemeMode()
    }

    private fun getThemeMode() {
        viewModelScope.launch {
            _settingUiState.emit(
                SettingUiState.GetThemeMode(
                    getThemeModeUseCase()
                )
            )
        }
    }

    fun setThemeMode(mode: Int) {
        viewModelScope.launch {
            setThemeModeUseCase(mode)
        }
    }
}