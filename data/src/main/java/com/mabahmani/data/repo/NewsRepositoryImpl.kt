package com.mabahmani.data.repo

import com.mabahmani.data.ds.RemoteDataSource
import com.mabahmani.domain.repo.NewsRepository
import com.mabahmani.domain.vo.NewsDetails
import com.mabahmani.domain.vo.common.NewsId
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource): NewsRepository {
    override suspend fun getNewsDetails(newsId: NewsId): Result<NewsDetails> {
        val remoteResult = remoteDataSource.getNewsDetails(newsId.value)

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.toNewsDetails())
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }

}