package com.mabahmani.domain.repo

import com.mabahmani.domain.vo.common.Trailer

interface TrailerRepository {

    suspend fun getAnticipatedTrailers(): Result<List<Trailer>>

    suspend fun getPopularTrailers(): Result<List<Trailer>>

    suspend fun getRecentTrailers(): Result<List<Trailer>>

    suspend fun getTrendingTrailers(): Result<List<Trailer>>
}