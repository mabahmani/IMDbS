package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.*
import com.mabahmani.domain.vo.common.EventId
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.domain.vo.common.NewsId
import com.mabahmani.domain.vo.enum.SuggestionType
import javax.inject.Inject

class GetNewsDetailsUseCase @Inject constructor(private val newsRepository: NewsRepository){
    suspend operator fun invoke(newsId: NewsId) = newsRepository.getNewsDetails(newsId)
}