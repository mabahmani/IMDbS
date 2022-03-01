package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.ChartRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val chartRepository: ChartRepository){
    suspend operator fun invoke() = chartRepository.getPopularMovies()
}