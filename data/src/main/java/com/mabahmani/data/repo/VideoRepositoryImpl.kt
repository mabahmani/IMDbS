package com.mabahmani.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mabahmani.data.ds.RemoteDataSource
import com.mabahmani.data.ps.NameVideosPagingSource
import com.mabahmani.data.ps.TitleImagesPagingSource
import com.mabahmani.data.ps.TitleVideosPagingSource
import com.mabahmani.domain.repo.VideoRepository
import com.mabahmani.domain.vo.VideoDetails
import com.mabahmani.domain.vo.common.*
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    VideoRepository {
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

    override suspend fun getTitleVideos(titleId: TitleId): Pager<Int, Video> {

        return Pager(PagingConfig(50)) {
            TitleVideosPagingSource(
                remoteDataSource,
                titleId
            )
        }
    }

    override suspend fun getNameVideos(nameId: NameId): Pager<Int, Video> {

        return Pager(PagingConfig(50)) {
            NameVideosPagingSource(
                remoteDataSource,
                nameId
            )
        }

    }
}