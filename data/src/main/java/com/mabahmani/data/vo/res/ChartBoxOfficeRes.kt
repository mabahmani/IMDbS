package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.common.*

data class ChartBoxOfficeRes(
    val weekendDate: String,
    val boxOfficeTitles: List<BoxOfficeTitle>
) {
    data class BoxOfficeTitle(
        val titleId: String,
        val title: String,
        val cover: String,
        val weekend: String,
        val gross: String,
        val weeks: String,
    )

    fun toBoxOffice(): BoxOffice{
        return BoxOffice(
            weekendDate,
            weekendDate,
            boxOfficeTitles.map {
                BoxOffice.BoxOfficeItem(
                    it.weekend,
                    it.gross,
                    it.weeks,
                    it.title,
                    TitleId(it.titleId),
                    null
                )
            }
        )
    }
}