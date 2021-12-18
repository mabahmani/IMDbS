package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.SearchRepository
import javax.inject.Inject

class SearchTitlesByKeywordsUseCase @Inject constructor(private val searchRepository: SearchRepository){
    suspend operator fun invoke(keyword: String) = searchRepository.searchTitles(keywords = listOf(keyword))
}