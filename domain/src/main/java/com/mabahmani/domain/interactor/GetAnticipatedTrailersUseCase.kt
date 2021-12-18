package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.ChartRepository
import com.mabahmani.domain.repo.SearchRepository
import com.mabahmani.domain.repo.TrailerRepository
import com.mabahmani.domain.vo.enum.SuggestionType
import javax.inject.Inject

class GetAnticipatedTrailersUseCase @Inject constructor(private val trailerRepository: TrailerRepository){
    suspend operator fun invoke() = trailerRepository.getAnticipatedTrailers()
}