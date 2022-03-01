package com.mabahmani.domain.repo

interface SettingRepository {

    fun getThemeMode() : Int

    suspend fun setThemeMode(mode: Int)

}