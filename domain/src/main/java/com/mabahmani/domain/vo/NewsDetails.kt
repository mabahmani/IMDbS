package com.mabahmani.domain.vo

import com.mabahmani.domain.vo.common.Image

data class NewsDetails(
    val title: String,
    val date: String,
    val author: String,
    val newsAgency: String,
    val image: Image,
    val content: String,
    val link: String
)
