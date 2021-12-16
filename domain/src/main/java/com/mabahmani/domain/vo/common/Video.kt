package com.mabahmani.domain.vo.common

data class Video(
    val videoId: VideoId,
    val preview: Image,
    val title: String,
    val runtime: String
    )
