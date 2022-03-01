package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.SettingRepository
import javax.inject.Inject

class GetThemeModeUseCase @Inject constructor(private val settingRepository: SettingRepository){
    operator fun invoke() = settingRepository.getThemeMode()
}