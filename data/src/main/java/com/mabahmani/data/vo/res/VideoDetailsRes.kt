package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.VideoDetails
import com.mabahmani.domain.vo.common.*

data class VideoDetailsRes(
    val highQ: String,
    val lowQ: String,
    val relatedVideos: List<RelatedVideo>,
    val relationCertificateRating: String,
    val relationCertificateRatingsBody: String,
    val relationGenres: List<String>,
    val relationIMDbRating: Int,
    val relationIsIMDbTVTitle: Boolean,
    val relationIsReleased: Boolean,
    val relationPoster: String,
    val relationReleaseDate: String,
    val relationReleaseYear: String,
    val relationRuntime: String,
    val relationTitle: String,
    val titleId: String,
    val videoCover: String,
    val videoDescription: String,
    val videoRuntime: String,
    val videoSubTitle: String,
    val videoTitle: String
) {
    data class RelatedVideo(
        val cover: String,
        val duration: String,
        val subtitle: String,
        val title: String,
        val titleId: String,
        val videoId: String
    )

    fun toVideoDetails(): VideoDetails {
        return VideoDetails(
            Image(videoCover),
            highQ,
            lowQ,
            videoTitle,
            Title(
                Image(relationPoster),
                relationIMDbRating.toString(),
                null,
                null,
                relationGenres.toString(),
                relationRuntime,
                relationTitle,
                relationReleaseYear,
                null,
                null,
                relationCertificateRating,
                null,
                null,
                null,
                TitleId(titleId),
                null
            ),
            relatedVideos.map {
                Video(VideoId(it.videoId), Image(it.cover), it.title, it.duration)
            }
        )
    }
}