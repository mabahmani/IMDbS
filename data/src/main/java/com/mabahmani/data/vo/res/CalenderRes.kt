package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.Calender
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.domain.vo.common.TitleLink

data class CalenderRes(
    val date: String,
    val titles: List<Title>
) {
    data class Title(
        val titleId: String,
        val title: String
    )

    fun toCalender(): Calender {
        return Calender(
            date,
            titles.map { TitleLink(it.title, TitleId(it.titleId)) }
        )
    }
}