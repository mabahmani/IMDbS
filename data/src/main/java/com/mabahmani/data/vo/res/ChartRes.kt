package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.Title
import com.mabahmani.domain.vo.common.TitleId

data class ChartRes(
    val cover: String,
    val imdbRating: Float,
    val link: String,
    val numberOfRating: Int,
    val rank: Int,
    val title: String,
    val titleId: String,
    val year: String
) {
    fun toTitle(): Title {
        return Title(
            Image(cover),
            imdbRating.toString(),
            numberOfRating.toString(),
            null,
            null,
            null,
            title,
            year,
            null,
            null,
            null,
            null,
            null,
            null,
            TitleId(titleId),
            null,
            null,
            null,
            null
        )
    }
}