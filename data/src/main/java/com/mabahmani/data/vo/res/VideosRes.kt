package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.Video
import com.mabahmani.domain.vo.common.VideoId

data class VideosRes(
    val avatar: String,
    val title: String,
    val videos: List<Video>
) {
    data class Video(
        val cover: String,
        val id: String,
        val title: String
    )

    fun toVideos(): List<com.mabahmani.domain.vo.common.Video>{
        return videos.map {
            Video(
                VideoId(it.id),
                Image(it.cover),
                it.title,
                ""
            )
        }


    }
}