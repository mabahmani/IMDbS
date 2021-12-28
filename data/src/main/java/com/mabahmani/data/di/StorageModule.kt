package com.mabahmani.data.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.mabahmani.data.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.Prefs.PREFS_NAME,Context.MODE_PRIVATE)
    }

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }
}

