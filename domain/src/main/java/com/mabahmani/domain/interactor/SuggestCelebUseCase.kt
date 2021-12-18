package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.SearchRepository
import com.mabahmani.domain.vo.enum.SuggestionType
import javax.inject.Inject

class SuggestCelebUseCase @Inject constructor(private val searchRepository: SearchRepository){
    suspend operator fun invoke(term: String) = searchRepository.getSuggestion(suggestionType = SuggestionType.CELEB, term)
}