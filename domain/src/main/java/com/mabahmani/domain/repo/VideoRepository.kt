package com.mabahmani.domain.repo

import androidx.paging.Pager
import com.mabahmani.domain.vo.VideoDetails
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.domain.vo.common.Video
import com.mabahmani.domain.vo.common.VideoId

interface VideoRepository {

    suspend fun getVideoDetails(videoId: VideoId) : Result<VideoDetails>

    suspend fun getTitleVideos(titleId: TitleId) :  Pager<Int, Video>

    suspend fun getNameVideos(nameId: NameId) : Pager<Int, Video>
}