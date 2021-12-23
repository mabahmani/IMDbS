package com.mabahmani.data.di

import CelebsProtoOuterClass
import EventsProtoOuterClass
import GenresProtoOuterClass
import HomeExtraProtoOuterClass
import HomeProtoOuterClass
import KeywordsProtoOuterClass
import TitlesProtoOuterClass
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.mabahmani.data.local.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    private val Context.homeDataStore: DataStore<HomeProtoOuterClass.HomeProto> by dataStore(
        fileName = "HomeProto.pb",
        serializer = HomeProtoSerializer
    )

    private val Context.homeExtraDataStore: DataStore<HomeExtraProtoOuterClass.HomeExtraProto> by dataStore(
        fileName = "HomeExtraProto.pb",
        serializer = HomeExtraProtoSerializer
    )

    private val Context.celebsDataStore: DataStore<CelebsProtoOuterClass.CelebsProto> by dataStore(
        fileName = "CelebsProto.pb",
        serializer = CelebsProtoSerializer
    )

    private val Context.eventsDataStore: DataStore<EventsProtoOuterClass.EventsProto> by dataStore(
        fileName = "EventsProto.pb",
        serializer = EventsProtoSerializer
    )

    private val Context.genresDataStore: DataStore<GenresProtoOuterClass.GenresProto> by dataStore(
        fileName = "GenresProto.pb",
        serializer = GenresProtoSerializer
    )

    private val Context.keywordsDataStore: DataStore<KeywordsProtoOuterClass.KeywordsProto> by dataStore(
        fileName = "KeywordsProto.pb",
        serializer = KeywordsProtoSerializer
    )

    private val Context.titlesDataStore: DataStore<TitlesProtoOuterClass.TitlesProto> by dataStore(
        fileName = "TitlesProto.pb",
        serializer = TitlesProtoSerializer
    )

    private val Context.top250DataStore: DataStore<TitlesProtoOuterClass.TitlesProto> by dataStore(
        fileName = "Top250Proto.pb",
        serializer = TitlesProtoSerializer
    )

    private val Context.topTv250DataStore: DataStore<TitlesProtoOuterClass.TitlesProto> by dataStore(
        fileName = "TopTv250Proto.pb",
        serializer = TitlesProtoSerializer
    )

    private val Context.popularTv250DataStore: DataStore<TitlesProtoOuterClass.TitlesProto> by dataStore(
        fileName = "PopularTv250Proto.pb",
        serializer = TitlesProtoSerializer
    )

    private val Context.popular250DataStore: DataStore<TitlesProtoOuterClass.TitlesProto> by dataStore(
        fileName = "Popular250Proto.pb",
        serializer = TitlesProtoSerializer
    )

    private val Context.bottom100DataStore: DataStore<TitlesProtoOuterClass.TitlesProto> by dataStore(
        fileName = "Bottom100Proto.pb",
        serializer = TitlesProtoSerializer
    )

    private val Context.anticipatedTrailersDataStore: DataStore<TrailersProtoOuterClass.TrailersProto> by dataStore(
        fileName = "AnticipatedTrailersProto.pb",
        serializer = TrailersProtoSerializer
    )

    private val Context.popularTrailersDataStore: DataStore<TrailersProtoOuterClass.TrailersProto> by dataStore(
        fileName = "PopularTrailersProto.pb",
        serializer = TrailersProtoSerializer
    )

    private val Context.recentTrailersDataStore: DataStore<TrailersProtoOuterClass.TrailersProto> by dataStore(
        fileName = "RecentTrailersProto.pb",
        serializer = TrailersProtoSerializer
    )

    private val Context.trendingTrailersDataStore: DataStore<TrailersProtoOuterClass.TrailersProto> by dataStore(
        fileName = "TrendingTrailersProto.pb",
        serializer = TrailersProtoSerializer
    )

    @Provides
    @Singleton
    fun provideHomeDataStore(@ApplicationContext appContext: Context): DataStore<HomeProtoOuterClass.HomeProto>{
        return appContext.homeDataStore
    }

    @Provides
    @Singleton
    fun provideHomeExtraDataStore(@ApplicationContext appContext: Context): DataStore<HomeExtraProtoOuterClass.HomeExtraProto>{
        return appContext.homeExtraDataStore
    }

    @Provides
    @Singleton
    fun provideCelebsDataStore(@ApplicationContext appContext: Context): DataStore<CelebsProtoOuterClass.CelebsProto>{
        return appContext.celebsDataStore
    }

    @Provides
    @Singleton
    fun provideEventsDataStore(@ApplicationContext appContext: Context): DataStore<EventsProtoOuterClass.EventsProto>{
        return appContext.eventsDataStore
    }

    @Provides
    @Singleton
    fun provideGenresDataStore(@ApplicationContext appContext: Context): DataStore<GenresProtoOuterClass.GenresProto>{
        return appContext.genresDataStore
    }

    @Provides
    @Singleton
    fun provideKeywordsDataStore(@ApplicationContext appContext: Context): DataStore<KeywordsProtoOuterClass.KeywordsProto>{
        return appContext.keywordsDataStore
    }

    @Provides
    @Singleton
    fun provideTitlesDataStore(@ApplicationContext appContext: Context): DataStore<TitlesProtoOuterClass.TitlesProto>{
        return appContext.titlesDataStore
    }

    @Provides
    @Singleton
    fun provideTop250TitlesDataStore(@ApplicationContext appContext: Context): DataStore<TitlesProtoOuterClass.TitlesProto>{
        return appContext.top250DataStore
    }

    @Provides
    @Singleton
    fun provideTopTv250TitlesDataStore(@ApplicationContext appContext: Context): DataStore<TitlesProtoOuterClass.TitlesProto>{
        return appContext.topTv250DataStore
    }

    @Provides
    @Singleton
    fun providePopular250TitlesDataStore(@ApplicationContext appContext: Context): DataStore<TitlesProtoOuterClass.TitlesProto>{
        return appContext.popular250DataStore
    }

    @Provides
    @Singleton
    fun providePopularTv250TitlesDataStore(@ApplicationContext appContext: Context): DataStore<TitlesProtoOuterClass.TitlesProto>{
        return appContext.popularTv250DataStore
    }

    @Provides
    @Singleton
    fun provideBottom100TitlesDataStore(@ApplicationContext appContext: Context): DataStore<TitlesProtoOuterClass.TitlesProto>{
        return appContext.bottom100DataStore
    }

    @Provides
    @Singleton
    fun provideAnticipatedTrailersDataStore(@ApplicationContext appContext: Context): DataStore<TrailersProtoOuterClass.TrailersProto>{
        return appContext.anticipatedTrailersDataStore
    }

    @Provides
    @Singleton
    fun provideRecentTrailersDataStore(@ApplicationContext appContext: Context): DataStore<TrailersProtoOuterClass.TrailersProto>{
        return appContext.recentTrailersDataStore
    }

    @Provides
    @Singleton
    fun provideTrendingTrailersDataStore(@ApplicationContext appContext: Context): DataStore<TrailersProtoOuterClass.TrailersProto>{
        return appContext.trendingTrailersDataStore
    }

    @Provides
    @Singleton
    fun providePopularTrailersDataStore(@ApplicationContext appContext: Context): DataStore<TrailersProtoOuterClass.TrailersProto>{
        return appContext.popularTrailersDataStore
    }

}

