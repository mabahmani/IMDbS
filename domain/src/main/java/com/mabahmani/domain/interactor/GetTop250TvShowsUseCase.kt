package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.ChartRepository
import com.mabahmani.domain.repo.SearchRepository
import com.mabahmani.domain.vo.enum.SuggestionType
import javax.inject.Inject

class GetTop250TvShowsUseCase @Inject constructor(private val chartRepository: ChartRepository){
    suspend operator fun invoke() = chartRepository.getTop250TvShows()
}