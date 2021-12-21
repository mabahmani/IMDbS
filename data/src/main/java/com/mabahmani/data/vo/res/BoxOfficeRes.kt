package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.common.BoxOffice
import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.TitleId

data class BoxOfficeRes(
    val boxOfficeTitles: List<BoxOfficeTitle>,
    val weekendDate: String
) {
    data class BoxOfficeTitle(
        val cover: String,
        val gross: String,
        val title: String,
        val titleId: String,
        val weekend: String,
        val weeks: String
    )

    fun toBoxOffice() : BoxOffice{
        return BoxOffice(
            startDate = weekendDate,
            endDate = weekendDate,
            boxOfficeItems = boxOfficeTitles.map { BoxOffice.BoxOfficeItem(
                weekendGross = it.weekend,
                gross = it.gross,
                weeks = it.weeks,
                title = it.title,
                titleId = TitleId(it.titleId),
                image = Image(it.cover)
            ) }
        )
    }
}