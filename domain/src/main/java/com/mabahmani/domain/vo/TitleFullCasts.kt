package com.mabahmani.domain.vo

import com.mabahmani.domain.vo.common.Cast
import com.mabahmani.domain.vo.common.Image

data class TitleFullCasts(
    val name: String,
    val year: String,
    val cover: Image,
    val casts: List<CastItem>
){
    data class CastItem(
        val title: String,
        val casts: List<Cast>
    )
}
