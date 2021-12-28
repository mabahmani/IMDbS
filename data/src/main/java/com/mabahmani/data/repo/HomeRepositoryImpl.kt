package com.mabahmani.data.repo

import com.mabahmani.data.ds.RemoteDataSource
import com.mabahmani.domain.repo.HomeRepository
import com.mabahmani.domain.vo.Home
import com.mabahmani.domain.vo.HomeExtra
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
): HomeRepository {
    override suspend fun getHome(): Result<Home> {

        val remoteResult = remoteDataSource.getHome()

        return if(remoteResult.isSuccess){
            try {
                Result.success(remoteResult.getOrNull()!!.data.toHome())
            }catch (ex: Exception){
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull()?: java.lang.Exception())
    }

    override suspend fun getHomeExtra(): Result<HomeExtra> {
        val remoteResult = remoteDataSource.getHomeExtra()

        return if(remoteResult.isSuccess){
            try {
                Result.success(remoteResult.getOrNull()!!.data.toHomeExtra())
            }catch (ex: Exception){
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull()?: java.lang.Exception())
    }
}