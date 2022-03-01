package com.mabahmani.imdb_scraping

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.mabahmani.domain.interactor.GetThemeModeUseCase
import dagger.hilt.android.HiltAndroidApp
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import timber.log.Timber
import javax.inject.Inject


@HiltAndroidApp
class MainApplication : Application() {

    @Inject lateinit var getThemeModeUseCase: GetThemeModeUseCase

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initCalligraphy()
        initTheme()
    }

    private fun initTheme() {
        AppCompatDelegate.setDefaultNightMode(getThemeModeUseCase())
    }

    private fun initTimber() {
        if(BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    private fun initCalligraphy() {
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath(getString(R.string.font_regular))
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
    }
}