package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.VideoDetails
import com.mabahmani.domain.vo.common.*

data class VideoDetailsRes(
    val highQ: String?,
    val lowQ: String?,
    val relatedVideos: List<RelatedVideo>?,
    val relationCertificateRating: String?,
    val relationCertificateRatingsBody: String?,
    val relationGenres: List<String>?,
    val relationIMDbRating: Float?,
    val relationIsIMDbTVTitle: Boolean?,
    val relationIsReleased: Boolean?,
    val relationPoster: String?,
    val relationReleaseDate: String?,
    val relationReleaseYear: String?,
    val relationRuntime: String?,
    val relationTitle: String?,
    val titleId: String?,
    val videoCover: String?,
    val videoDescription: String?,
    val videoRuntime: String?,
    val videoSubTitle: String?,
    val videoTitle: String?
) {
    data class RelatedVideo(
        val cover: String?,
        val duration: String?,
        val subtitle: String?,
        val title: String?,
        val titleId: String?,
        val videoId: String?
    )

    fun toVideoDetails(): VideoDetails {
        return VideoDetails(
            Image(videoCover.orEmpty()),
            highQ.orEmpty(),
            lowQ.orEmpty(),
            videoDescription.orEmpty(),
            Title(
                Image(relationPoster.orEmpty()),
                relationIMDbRating.toString(),
                null,
                null,
                relationGenres?.joinToString(),
                relationRuntime.orEmpty(),
                relationTitle.orEmpty(),
                relationReleaseDate.orEmpty(),
                relationReleaseDate.orEmpty(),
                relationReleaseDate.orEmpty(),
                relationCertificateRating.orEmpty(),
                null,
                null,
                null,
                TitleId(titleId.orEmpty()),
                null,
                null,
                null,
                null
            ),
            relatedVideos?.map {
                Video(VideoId(it.videoId.orEmpty()), Image(it.cover.orEmpty()), it.title.orEmpty(), it.duration.orEmpty(), it.subtitle.orEmpty())
            }?: listOf()
        )
    }
}