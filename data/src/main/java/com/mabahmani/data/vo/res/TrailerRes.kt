package com.mabahmani.data.vo.res

import com.mabahmani.data.util.toHumanReadableTime
import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.domain.vo.common.Trailer
import com.mabahmani.domain.vo.common.VideoId

data class TrailerRes(
    val title: String?,
    val titleCover: String?,
    val titleId: String?,
    val titleReleaseDay: Int?,
    val titleReleaseMonth: Int?,
    val titleReleaseYear: Int?,
    val videoDescription: String?,
    val videoId: String?,
    val videoName: String?,
    val videoRuntime: Int?,
    val videoThumbnail: String?
){
    fun toTrailer(): Trailer{
        return Trailer(
            title.orEmpty(),
            videoDescription.orEmpty(),
            TitleId(titleId.orEmpty()),
            VideoId(videoId.orEmpty()),
            videoRuntime.toHumanReadableTime(),
            Image(videoThumbnail.orEmpty()),
            Image(titleCover.orEmpty())
        )
    }
}