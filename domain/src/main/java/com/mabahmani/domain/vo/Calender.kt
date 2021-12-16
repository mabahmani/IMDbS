package com.mabahmani.domain.vo

import com.mabahmani.domain.vo.common.TitleLink

data class Calender(
    val date: String,
    val titles: List<TitleLink>
)