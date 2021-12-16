package com.mabahmani.domain.vo

import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.Title
import com.mabahmani.domain.vo.common.Video

data class VideoDetails(
    val preview: Image,
    val highQualityUrl: String,
    val lowQualityUrl: String,
    val caption: String,
    val relatedTitle: Title,
    val relatedVideos: List<Video>
)
