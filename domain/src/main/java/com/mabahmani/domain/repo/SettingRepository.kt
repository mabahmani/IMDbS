package com.mabahmani.domain.repo

import com.mabahmani.domain.vo.HomeExtra

interface SettingRepository {

    fun getThemeMode() : Int

    suspend fun setThemeMode(mode: Int)

}