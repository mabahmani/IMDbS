package com.mabahmani.data.repo

import com.mabahmani.data.ds.RemoteDataSource
import com.mabahmani.domain.repo.TitleRepository
import com.mabahmani.domain.repo.TrailerRepository
import com.mabahmani.domain.vo.*
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.domain.vo.common.Trailer
import javax.inject.Inject

class TrailerRepositoryImpl  @Inject constructor(private val remoteDataSource: RemoteDataSource): TrailerRepository {
    override suspend fun getAnticipatedTrailers(): Result<List<Trailer>> {
        val remoteResult = remoteDataSource.getTrailersAnticipated()

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.map { it.toTrailer() })
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }

    override suspend fun getPopularTrailers(): Result<List<Trailer>> {
        val remoteResult = remoteDataSource.getTrailersPopular()

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.map { it.toTrailer() })
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())    }

    override suspend fun getRecentTrailers(): Result<List<Trailer>> {
        val remoteResult = remoteDataSource.getTrailersRecent()

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.map { it.toTrailer() })
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())    }

    override suspend fun getTrendingTrailers(): Result<List<Trailer>> {
        val remoteResult = remoteDataSource.getTrailersTrending()

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.map { it.toTrailer() })
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())    }


}