package com.mabahmani.domain.repo

import com.mabahmani.domain.vo.NewsDetails
import com.mabahmani.domain.vo.common.NewsId

interface NewsRepository {

    suspend fun getNewsDetails(newsId: NewsId) : Result<NewsDetails>
}