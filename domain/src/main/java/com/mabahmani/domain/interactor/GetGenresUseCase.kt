package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.SearchRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(private val searchRepository: SearchRepository){
    suspend operator fun invoke() = searchRepository.getGenres()
}