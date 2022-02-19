package com.mabahmani.imdb_scraping.ui.main.home.state

import com.mabahmani.domain.vo.Home
import com.mabahmani.domain.vo.HomeExtra
import com.mabahmani.domain.vo.common.Trailer

sealed class SettingUiState {
    object Loading: SettingUiState()
    class GetThemeMode(val mode: Int) : SettingUiState()
}
