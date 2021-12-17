package com.mabahmani.domain.repo

import com.mabahmani.domain.vo.VideoDetails
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.domain.vo.common.Video
import com.mabahmani.domain.vo.common.VideoId

interface VideoRepository {

    suspend fun getVideoDetails(videoId: VideoId) : Result<VideoDetails>

    suspend fun getTitleVideos(titleId: TitleId) : Result<List<Video>>

    suspend fun getNameVideos(nameId: NameId) : Result<List<Video>>
}