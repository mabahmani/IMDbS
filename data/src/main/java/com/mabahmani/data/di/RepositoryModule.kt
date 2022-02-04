package com.mabahmani.data.di

import com.mabahmani.data.repo.ChartRepositoryImpl
import com.mabahmani.data.repo.HomeRepositoryImpl
import com.mabahmani.data.repo.SearchRepositoryImpl
import com.mabahmani.data.repo.TrailerRepositoryImpl
import com.mabahmani.domain.repo.ChartRepository
import com.mabahmani.domain.repo.HomeRepository
import com.mabahmani.domain.repo.SearchRepository
import com.mabahmani.domain.repo.TrailerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository

    @Binds
    abstract fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    abstract fun bindChartRepository(
        chartRepositoryImpl: ChartRepositoryImpl
    ): ChartRepository

    @Binds
    abstract fun bindTrailerRepository(
        trailerRepositoryImpl: TrailerRepositoryImpl
    ): TrailerRepository
}