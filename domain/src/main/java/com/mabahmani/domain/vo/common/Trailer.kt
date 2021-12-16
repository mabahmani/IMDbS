package com.mabahmani.domain.vo.common

data class Trailer(
    val title: String,
    val caption: String,
    val titleId: TitleId,
    val videoId: VideoId,
    val duration: String,
    val videoPreview: Image,
    val titleCover: Image
)