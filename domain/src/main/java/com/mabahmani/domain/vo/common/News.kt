package com.mabahmani.domain.vo.common

data class News(
    val date: String,
    val image: Image,
    val title: String,
    val source: String,
    val newsId: NewsId
)