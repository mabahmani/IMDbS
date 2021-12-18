package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.HomeRepository
import javax.inject.Inject

class GetHomeExtraUseCase @Inject constructor(private val homeRepository: HomeRepository){
    suspend operator fun invoke() = homeRepository.getHomeExtra()
}