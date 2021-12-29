package com.mabahmani.data.repo

import com.mabahmani.data.ds.RemoteDataSource
import com.mabahmani.domain.repo.VideoRepository
import com.mabahmani.domain.vo.VideoDetails
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.domain.vo.common.Video
import com.mabahmani.domain.vo.common.VideoId
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource): VideoRepository {
    override suspend fun getVideoDetails(videoId: VideoId): Result<VideoDetails> {
        val remoteResult = remoteDataSource.getVideoDetails(videoId.value)

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.toVideoDetails())
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }

    override suspend fun getTitleVideos(titleId: TitleId, page: String): Result<List<Video>> {
        val remoteResult = remoteDataSource.getTitleVideos(titleId.value, page)

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.toVideos())
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }

    override suspend fun getNameVideos(nameId: NameId, page: String): Result<List<Video>> {
        val remoteResult = remoteDataSource.getNameVideos(nameId.value, page)

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.toVideos())
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())    }
}