package com.mabahmani.domain.vo.common


data class BoxOffice(
    val startDate: String,
    val endDate: String,
    val boxOfficeItems: List<BoxOfficeItem>

){
    data class BoxOfficeItem(
        val weekendGross: String,
        val gross: String,
        val weeks: String,
        val title: String,
        val titleId: TitleId,
        val image: Image?,
    )
}
