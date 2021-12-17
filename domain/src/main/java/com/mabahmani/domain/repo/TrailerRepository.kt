package com.mabahmani.domain.repo

import com.mabahmani.domain.vo.common.Trailer

interface TrailerRepository {

    suspend fun getAnticipatedTrailers(): List<Trailer>

    suspend fun getPopularTrailers(): List<Trailer>

    suspend fun getRecentTrailers(): List<Trailer>

    suspend fun getTrendingTrailers(): List<Trailer>
}