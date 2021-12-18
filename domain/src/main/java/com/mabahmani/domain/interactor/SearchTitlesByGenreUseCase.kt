package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.SearchRepository
import javax.inject.Inject

class SearchTitlesByGenreUseCase @Inject constructor(private val searchRepository: SearchRepository){
    suspend operator fun invoke(genre: String) = searchRepository.searchTitles(genres = listOf(genre))
}