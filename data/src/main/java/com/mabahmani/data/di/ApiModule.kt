package com.mabahmani.data.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.mabahmani.data.remote.CacheInterceptor
import com.mabahmani.data.remote.ImdbScrapingApiService
import com.mabahmani.data.remote.ImdbSuggestionApiService
import com.mabahmani.data.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {

        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        val cacheSize = 10 * 1024 * 1024 // 10 MiB

        val cache = Cache(httpCacheDirectory, cacheSize.toLong())

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)


        val builder = OkHttpClient().newBuilder()
            .addNetworkInterceptor(CacheInterceptor())
            .cache(cache)
            .addInterceptor(logging)
        return builder.build()
    }

    @Provides
    fun provideImdbScrapingApiService(okHttpClient: OkHttpClient): ImdbScrapingApiService {

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.IMDB_SCRAPING_BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .create()
                )
            )
            .client(okHttpClient)
            .build()

        return retrofit.create(ImdbScrapingApiService::class.java)
    }

    @Provides
    fun provideImdbSuggestionApiService(): ImdbSuggestionApiService {

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.IMDB_SUGGESTION_BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .create()
                )
            )
            .build()

        return retrofit.create(ImdbSuggestionApiService::class.java)
    }
}

