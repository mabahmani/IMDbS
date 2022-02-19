package com.mabahmani.data.repo

import android.content.SharedPreferences
import androidx.core.content.edit
import com.mabahmani.data.util.Constants
import com.mabahmani.domain.repo.SettingRepository
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): SettingRepository {
    override fun getThemeMode(): Int {
        return sharedPreferences.getInt(Constants.Prefs.THEME_MODE, -1)
    }

    override suspend fun setThemeMode(mode: Int) {
        sharedPreferences.edit {
            putInt(Constants.Prefs.THEME_MODE, mode)
        }
    }
}