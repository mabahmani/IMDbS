package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.TrailerRepository
import javax.inject.Inject

class GetAnticipatedTrailersUseCase @Inject constructor(private val trailerRepository: TrailerRepository){
    suspend operator fun invoke() = trailerRepository.getAnticipatedTrailers()
}