package com.mabahmani.imdb_scraping.ui.main.home.state

sealed class SettingUiState {
    object Loading: SettingUiState()
    class GetThemeMode(val mode: Int) : SettingUiState()
}
