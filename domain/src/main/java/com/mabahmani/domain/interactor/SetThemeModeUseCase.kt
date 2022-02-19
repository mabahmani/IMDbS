package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.SearchRepository
import com.mabahmani.domain.repo.SettingRepository
import com.mabahmani.domain.vo.enum.SuggestionType
import javax.inject.Inject

class SetThemeModeUseCase @Inject constructor(private val settingRepository: SettingRepository){
    suspend operator fun invoke(mode: Int) = settingRepository.setThemeMode(mode)
}