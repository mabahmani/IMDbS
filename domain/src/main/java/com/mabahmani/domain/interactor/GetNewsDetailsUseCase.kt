package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.NewsRepository
import com.mabahmani.domain.vo.common.NewsId
import javax.inject.Inject

class GetNewsDetailsUseCase @Inject constructor(private val newsRepository: NewsRepository){
    suspend operator fun invoke(newsId: NewsId) = newsRepository.getNewsDetails(newsId)
}