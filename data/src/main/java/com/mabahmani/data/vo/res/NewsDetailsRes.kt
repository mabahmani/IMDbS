package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.NewsDetails
import com.mabahmani.domain.vo.common.Image

data class NewsDetailsRes(
    val author: String?,
    val date: String?,
    val image: String?,
    val link: String?,
    val news: String?,
    val newsAgency: String?,
    val title: String?
){
    fun toNewsDetails(): NewsDetails{
        return NewsDetails(
            title.orEmpty(),
            date.orEmpty(),
            author.orEmpty(),
            newsAgency.orEmpty(),
            Image(image.orEmpty()),
            news.orEmpty(),
            link.orEmpty()
        )
    }
}