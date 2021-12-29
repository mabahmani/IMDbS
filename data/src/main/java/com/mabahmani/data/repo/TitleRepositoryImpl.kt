package com.mabahmani.data.repo

import com.mabahmani.data.ds.RemoteDataSource
import com.mabahmani.domain.repo.TitleRepository
import com.mabahmani.domain.vo.*
import com.mabahmani.domain.vo.common.TitleId
import javax.inject.Inject

class TitleRepositoryImpl  @Inject constructor(private val remoteDataSource: RemoteDataSource): TitleRepository {
    override suspend fun getTitleDetails(titleId: TitleId): Result<TitleDetails> {

        val remoteResult = remoteDataSource.getTitleDetails(titleId.value)

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.toTitleDetails())
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }

    override suspend fun getTitleAwards(titleId: TitleId): Result<TitleAwards> {

        val remoteResult = remoteDataSource.getTitleAwards(titleId.value)

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.toTitleAward())
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }

    override suspend fun getTitleFullCasts(titleId: TitleId): Result<TitleFullCasts> {

        val remoteResult = remoteDataSource.getTitleFullCredits(titleId.value)

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.toTitleFullCasts())
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }

    override suspend fun getTitleParentsGuide(titleId: TitleId): Result<TitleParentsGuide> {

        val remoteResult = remoteDataSource.getTitleParentalGuide(titleId.value)

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.toTitleParentsGuide())
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }

    override suspend fun getTitleTechnicalSpecs(titleId: TitleId): Result<TitleTechnicalSpecs> {

        val remoteResult = remoteDataSource.getTitleTechnicalSpecs(titleId.value)

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.toTitleTechnicalSpecs())
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }
}