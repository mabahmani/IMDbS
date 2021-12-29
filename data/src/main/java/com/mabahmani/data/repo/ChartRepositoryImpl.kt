package com.mabahmani.data.repo

import com.mabahmani.data.ds.RemoteDataSource
import com.mabahmani.domain.repo.ChartRepository
import com.mabahmani.domain.vo.common.BoxOffice
import com.mabahmani.domain.vo.common.Title
import javax.inject.Inject

class ChartRepositoryImpl @Inject constructor (private val remoteDataSource: RemoteDataSource) : ChartRepository {

    override suspend fun getTop250Movies(): Result<List<Title>> {
        val remoteResult = remoteDataSource.getChartTop250()

        return if(remoteResult.isSuccess){
            try {
                Result.success(remoteResult.getOrNull()!!.data.map { it.toTitle() })
            }catch (ex: Exception){
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull()?: java.lang.Exception())
    }

    override suspend fun getTop250TvShows(): Result<List<Title>> {
        val remoteResult = remoteDataSource.getChartTopTv250()

        return if(remoteResult.isSuccess){
            try {
                Result.success(remoteResult.getOrNull()!!.data.map { it.toTitle() })
            }catch (ex: Exception){
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull()?: java.lang.Exception())
    }

    override suspend fun getPopularMovies(): Result<List<Title>> {
        val remoteResult = remoteDataSource.getChartPopular()

        return if(remoteResult.isSuccess){
            try {
                Result.success(remoteResult.getOrNull()!!.data.map { it.toTitle() })
            }catch (ex: Exception){
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull()?: java.lang.Exception())
    }

    override suspend fun getPopularTvShows(): Result<List<Title>> {
        val remoteResult = remoteDataSource.getChartTvPopular()

        return if(remoteResult.isSuccess){
            try {
                Result.success(remoteResult.getOrNull()!!.data.map { it.toTitle() })
            }catch (ex: Exception){
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull()?: java.lang.Exception())
    }

    override suspend fun getBottom100Movies(): Result<List<Title>> {
        val remoteResult = remoteDataSource.getChartBottom100()

        return if(remoteResult.isSuccess){
            try {
                Result.success(remoteResult.getOrNull()!!.data.map { it.toTitle() })
            }catch (ex: Exception){
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull()?: java.lang.Exception())
    }

    override suspend fun getBoxOffice(): Result<BoxOffice> {
        val remoteResult = remoteDataSource.getChartBoxOffice()

        return if(remoteResult.isSuccess){
            try {
                Result.success(remoteResult.getOrNull()!!.data.toBoxOffice())
            }catch (ex: Exception){
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull()?: java.lang.Exception())
    }
}